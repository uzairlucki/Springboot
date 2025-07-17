package com.dps.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
@Slf4j
@RestController
@RequestMapping("/api")
public class LocaleController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/locale", produces = "application/json; charset=UTF-8")
    public Map<String, String> getTranslations(@RequestParam(defaultValue = "en") String lang) {
        log.info("inside getTranslations");
        log.info("lang: ",lang);
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        Map<String, String> messages = new HashMap<>();
        bundle.keySet().forEach(key -> {
            messages.put(key, messageSource.getMessage(key, null, locale));
        });

        return messages;
    }
}
