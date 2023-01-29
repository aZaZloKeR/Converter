package com.example.converter.contoller;

import com.example.converter.service.ConversionLogService;
import com.example.converter.service.ConvertService;
import com.example.converter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class ConversionController {
    private final ConversionLogService conversionLogService;
    private final UserService userService;
    private final ConvertService convertService;

    @PostMapping("/conversion")
    public String convert(@RequestParam("typeOfConversion") String type, @RequestParam("dataForConversion") String dataForConversion,
                          Principal principal, Model model){
        String convertedData;
        try {
            convertedData = convertService.convert(dataForConversion.toLowerCase(Locale.ROOT),type);
            model.addAttribute("convertedData",convertedData);
            conversionLogService.saveConversionLog(userService.getUserByLogin(principal.getName()),type,dataForConversion,convertedData, LocalDateTime.now());
        }catch (NumberFormatException exception){
            model.addAttribute("convertedData",exception.getMessage());
        }
        return "conversion";
    }
}
