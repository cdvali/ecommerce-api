package com.vtech.ecommerce.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VersionController {

    @Value("${app.version:unknown}")  // Default to "unknown" if not set
    private String appVersion;

    @GetMapping("/version")
    public Map<String, String> getAppVersion() {
        return Map.of("version", appVersion);
    }
}