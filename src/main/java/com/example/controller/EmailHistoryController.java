package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.HttpRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/email_history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService historyService;

    @GetMapping("/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(historyService.getByEmail(email));
    }

    @GetMapping("/date{date}")
    public ResponseEntity<?> getByEmailByGivenDate(@PathVariable("date") LocalDate date) {

        return ResponseEntity.ok(historyService.getEmailByGivenDate(date));
    }

    @GetMapping("/adm")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                @RequestParam(value = "size", defaultValue = "6") Integer size,
                                                                HttpServletRequest request) {
        HttpRequestUtil.getProfileId(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(historyService.pagination(page, size));
    }

}
