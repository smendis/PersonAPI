package com.example.sampleapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource("classpath:work.properties")
@ConfigurationProperties("work.core")
public class WorkCore {
    private String email;

    private String placeholderImage;
}
