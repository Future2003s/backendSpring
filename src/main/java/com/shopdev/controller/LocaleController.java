package com.shopdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/locale")
@CrossOrigin(origins = "*")
public class LocaleController {

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/change")
    public ResponseEntity<Map<String, Object>> changeLocale(
            @RequestParam String lang,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        try {
            Locale newLocale = new Locale(lang);
            localeResolver.setLocale(request, response, newLocale);
            
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", true);
            responseMap.put("message", "Language changed successfully");
            responseMap.put("locale", lang);
            
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("success", false);
            errorMap.put("message", "Failed to change language");
            errorMap.put("error", e.getMessage());
            
            return ResponseEntity.badRequest().body(errorMap);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentLocale() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("locale", currentLocale.getLanguage());
        responseMap.put("displayName", currentLocale.getDisplayName());
        
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/messages")
    public ResponseEntity<Map<String, String>> getMessages(@RequestParam(required = false) String lang) {
        Locale locale = lang != null ? new Locale(lang) : LocaleContextHolder.getLocale();
        
        Map<String, String> messages = new HashMap<>();
        
        // Common messages
        messages.put("common.loading", messageSource.getMessage("common.loading", null, locale));
        messages.put("common.save", messageSource.getMessage("common.save", null, locale));
        messages.put("common.cancel", messageSource.getMessage("common.cancel", null, locale));
        messages.put("common.edit", messageSource.getMessage("common.edit", null, locale));
        messages.put("common.delete", messageSource.getMessage("common.delete", null, locale));
        messages.put("common.create", messageSource.getMessage("common.create", null, locale));
        messages.put("common.search", messageSource.getMessage("common.search", null, locale));
        messages.put("common.filter", messageSource.getMessage("common.filter", null, locale));
        messages.put("common.actions", messageSource.getMessage("common.actions", null, locale));
        messages.put("common.status", messageSource.getMessage("common.status", null, locale));
        messages.put("common.name", messageSource.getMessage("common.name", null, locale));
        messages.put("common.description", messageSource.getMessage("common.description", null, locale));
        messages.put("common.price", messageSource.getMessage("common.price", null, locale));
        messages.put("common.quantity", messageSource.getMessage("common.quantity", null, locale));
        messages.put("common.category", messageSource.getMessage("common.category", null, locale));
        messages.put("common.brand", messageSource.getMessage("common.brand", null, locale));
        
        // Navigation messages
        messages.put("navigation.dashboard", messageSource.getMessage("navigation.dashboard", null, locale));
        messages.put("navigation.products", messageSource.getMessage("navigation.products", null, locale));
        messages.put("navigation.orders", messageSource.getMessage("navigation.orders", null, locale));
        messages.put("navigation.categories", messageSource.getMessage("navigation.categories", null, locale));
        messages.put("navigation.brands", messageSource.getMessage("navigation.brands", null, locale));
        messages.put("navigation.users", messageSource.getMessage("navigation.users", null, locale));
        messages.put("navigation.analytics", messageSource.getMessage("navigation.analytics", null, locale));
        messages.put("navigation.settings", messageSource.getMessage("navigation.settings", null, locale));
        
        return ResponseEntity.ok(messages);
    }
}
