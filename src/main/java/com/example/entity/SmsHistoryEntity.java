package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "email")
    private String phone;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
