package com.example.sampleapi.config;

import com.example.sampleapi.logic.ImageValidator;
import com.example.sampleapi.logic.ProfileImageDownloder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootConfiguration
public class AppConfig {
    @Bean
    public ImageValidator getImageValidator(){
        System.out.println("Initializing ImageValidator...");
        return new ImageValidator();
    }

    @Autowired
    private WorkCore workCore;

    @Bean
    public ProfileImageDownloder getProfileImageDownloader(){
        System.out.println("Initializing Profile Image Downloader with "+ workCore.getPlaceholderImage()+"...");
        return new ProfileImageDownloder(workCore.getPlaceholderImage());
    }
}
