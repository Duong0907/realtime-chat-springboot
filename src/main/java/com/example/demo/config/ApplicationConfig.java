package com.example.demo.config;

import com.cloudinary.Cloudinary;
import com.example.demo.image_upload.CloudinaryUploadStrategy;
import com.example.demo.image_upload.FileUploadStrategy;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

    @Value("${CLOUDINARY_CLOUD_NAME}")
    private String cloudName;
    @Value("${CLOUDINARY_API_KEY}")
    private String apiKey;
    @Value("${CLOUDINARY_API_SECRET}")
    private String apiSecret;
    @Value("${CLOUDINARY_SECURE}")
    private String secure;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(this.userRepository);
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", secure);
        return new Cloudinary(config);
    }

    @Bean
    public FileUploadStrategy fileUploadStrategy(Cloudinary cloudinary) {
        return new CloudinaryUploadStrategy(cloudinary);

        // Able to switch between Cloudinary and AWS S3 here
        // By change the return to S3Client
    }
}
