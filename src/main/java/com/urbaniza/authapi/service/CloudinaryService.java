package com.urbaniza.authapi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public Map uploadImage(MultipartFile file, String folderName) throws IOException {
        Map<String, Object> options = new HashMap<>();
        options.put("folder", folderName);

        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    public Map deleteImage(String publicId) throws IOException {
        if (cloudinary == null) {
            throw new IllegalStateException("Cloudinary service not initialized.");
        }
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}