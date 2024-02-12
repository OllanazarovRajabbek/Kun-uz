package com.example.controller;

import com.example.dto.history.SmsHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.SmsHistoryService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/sms_history")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService historyService;

    @GetMapping("/{phone}")
    public ResponseEntity<?> getByEmail(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(historyService.getByPhone(phone));
    }

    @GetMapping("/date{date}")
    public ResponseEntity<?> getByEmailByGivenDate(@PathVariable("date") LocalDate date) {

        return ResponseEntity.ok(historyService.getEmailByGivenDate(date));
    }

    @GetMapping("/adm")
    public ResponseEntity<PageImpl<SmsHistoryDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                              @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                              HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(historyService.pagination(page, size));
    }
}
