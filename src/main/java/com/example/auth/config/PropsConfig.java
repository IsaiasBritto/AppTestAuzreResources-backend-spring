package com.example.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GoogleProperties.class,
        AppJwtProperties.class,
        AzureProperties.class
})
public class PropsConfig {}
