package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Authorization Api list", description = "Api list for Authorization")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

//    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Operation(summary = "Api for login", description = "This api used for authorization")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO auth) {
        log.trace("Login In Trace");
        log.debug("Login In Debug");
        log.info("Login {} ", auth.getEmail());
        log.warn("Login {} ", auth.getEmail());
        log.error("Login {} ", auth.getEmail());
        return ResponseEntity.ok(authService.auth(auth));
    }

    @PostMapping("/registration")
    @Operation(summary = "Api for registration", description = "users are registered through this method")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("Registration {} ", dto.getEmail());
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/verification/email/{jwt}")
    @Operation(summary = "Api for email verification",description = "This api is used to verify email")
    public ResponseEntity<String> emailVerification(@PathVariable("jwt") String jwt) {
        log.info("EmailVerification {} ", jwt);
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }

    @GetMapping("/verification/gmail/{jwt}")
    @Operation(summary = "Api gmail for verification",description = "This api is used for gmail verification")
    public ResponseEntity<String> gmailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.gmailVerification(jwt));
    }

}
