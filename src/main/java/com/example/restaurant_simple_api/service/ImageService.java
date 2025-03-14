package com.example.restaurant_simple_api.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {
    private Cloudinary cloudinary;
    public ImageService(@Value("${cloudinary.cloud.name}") String cloudName,
                        @Value("${cloudinary.api.key}") String apiKey,
                        @Value("${cloudinary.api.secret}") String apiSecret) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        this.cloudinary = new Cloudinary(config);
    }
    public String uploadImage(MultipartFile file) {
        try {
            // Upload the image to Cloudinary and get the result as a map
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), new HashMap<>());

            // Extract the image URL from the upload result
            String imageUrl = (String) uploadResult.get("url");

            // Return the URL of the uploaded image
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }
}
