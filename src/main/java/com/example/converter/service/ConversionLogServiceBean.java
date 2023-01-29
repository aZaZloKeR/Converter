package com.example.converter.service;

import com.example.converter.db.entities.ConversionLog;
import com.example.converter.db.entities.User;
import com.example.converter.db.repositories.ConversionLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConversionLogServiceBean implements ConversionLogService{
    private final ConversionLogRepo conversionLogRepo;
    @Override
    public void saveConversionLog(User user, String type, String innerValue, String outerValue, LocalDateTime create) {
        ConversionLog conversionLog = new ConversionLog();
        conversionLog.setUser(user);
        conversionLog.setType(type);
        conversionLog.setInnerValue(innerValue);
        conversionLog.setOuterValue(outerValue);
        conversionLog.setConversionDate(create);
        conversionLogRepo.save(conversionLog);
    }
}
