package com.example.converter.service;

import com.example.converter.db.entities.User;

import java.time.LocalDateTime;

public interface ConversionLogService {
    void saveConversionLog(User user, String type, String innerValue, String outerValue, LocalDateTime create);
}
