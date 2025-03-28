package com.example.MindHaven_BE.configuration;



import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary uploader() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", System.getenv("CLOUD_NAME"));
        config.put("api_key", System.getenv("CLOUD_APIKEY"));
        config.put("api_secret", System.getenv("CLOUD_API_SECRET"));
        return new Cloudinary(config);
    }
}
