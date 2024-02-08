package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.EmailSendHistoryRepository;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import com.example.util.RandomSmsCodeUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MailSenderService mailSender;
    @Autowired
    private SmsServerService smsServerService;
    @Autowired
    private EmailHistoryService historyService;
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Autowired
    private EmailSendHistoryRepository historyRepository;


    public ProfileDTO auth(AuthDTO profile) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPassword(profile.getEmail(), MD5Util.encode(profile.getPassword()));

        if (optional.isEmpty()) {
            log.warn("Email or Password is wrong {} ", profile.getEmail());
            throw new ArithmeticException("Email or Password is wrong");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("You are already registered {} ", profile.getEmail());
            throw new AppBadException("You are already registered");
        }
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getId(), entity.getRole()));

        return dto;
    }

    public Boolean registration(RegistrationDTO dto) {
        if (dto.getName() == null || dto.getName().trim().length() <= 2) {
            throw new AppBadException("Name required");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() <= 2) {
            throw new AppBadException("Surname required");
        }
        if (!dto.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-_]).{5,}$")) {
            throw new AppBadException("Password required");
        }
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("Email exists");
            }
        }

        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (historyRepository.countSendEmail(dto.getEmail(), from, to) >= 4) {
            throw new AppBadException("To many attempt. Please try after 1 minute.");
        }

        if (dto.getEmail() != null && dto.getPhone() == null) {
            registrationEmail(dto);
        } else if (dto.getPhone() != null && dto.getEmail() != null) {
            registrationSms(dto);
        }
        return true;
    }

    public void registrationEmail(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get()); // delete
            } else {
                throw new AppBadException("Email exists");
            }
        }
        // create
        ProfileEntity entity = getEntity(dto);
        profileRepository.save(entity);

        //send verification code (email/sms)
        String jwt = JWTUtil.encodeForEmail(entity.getId());
        /* String text = "Hello. \n To complete registration please link to the following link\n"
                + "http://localhost:8080/auth/verification/email/" + jwt;*/

        String text = "<h1 style=\"text-align: center\">Hello %s</h1>\n" +
                "<p style=\"background-color: indianred; color: white; padding: 30px\">To complete registration please link to the following link</p>\n" +
                "<a style=\" background-color: #f44336;\n" +
                "  color: white;\n" +
                "  padding: 14px 25px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\" href=\"http://localhost:8080/auth/verification/email/%s\n" +
                "\">Click</a>\n" +
                "<br>\n";
        text = String.format(text, entity.getName(), jwt);

        mailSender.sendGmail(dto.getEmail(), "Complete registration", text);

        //history
        historyService.createEmail(dto, text);
    }

    public void registrationSms(RegistrationDTO dto) {
        // check
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get()); // delete
                // or
                //send verification code (email/sms)
            } else {
                throw new AppBadException("Email exists");
            }
        }
        // create
        ProfileEntity entity = getEntity(dto);
        profileRepository.save(entity);

        String code = RandomSmsCodeUtil.getRandomSmsCode();
        smsServerService.send(dto.getPhone(), "KunUz verification code: ", code);

        //history
        smsHistoryService.createSms(dto, code);
    }

    public String emailVerification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decode(jwt);

            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (!optional.isPresent()) {
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
        } catch (JwtException e) {
            throw new AppBadException("Please tyre again.");
        }
        return "Success";
    }

    public String gmailVerification(String jwt) {
        try {
            JwtDTO jwtDTO = JWTUtil.decode(jwt);
            Optional<ProfileEntity> optional = profileRepository.findById(jwtDTO.getId());
            if (optional.isEmpty()) {
                throw new AppBadException("Profile not found");
            }
            ProfileEntity entity = optional.get();
            if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
                throw new AppBadException("Profile in wrong status");
            }
            profileRepository.updateStatus(entity.getId(), ProfileStatus.ACTIVE);
        } catch (JwtException e) {
            throw new AppBadException("Please tyre again");
        }
        return "Success";
    }

    public ProfileEntity getEntity(RegistrationDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.encode(dto.getPassword()));
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.USER);
        return entity;
    }

}
