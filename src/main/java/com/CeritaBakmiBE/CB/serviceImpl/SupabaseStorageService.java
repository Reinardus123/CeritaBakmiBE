package com.CeritaBakmiBE.CB.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supaBaseUrl;

    @Value("${supabase.service-role-key}")
    private String serviceRoleKey;

    private final RestClient restClient;

    public SupabaseStorageService() {
        this.restClient = RestClient.create();
    }

    public String upload(MultipartFile image) throws IOException{

        String fileName = UUID.randomUUID()
                + "_"
                +image.getOriginalFilename();

        String bucketName = "menu-images";

        String url = supaBaseUrl
                + "/storage/v1/object/"
                +bucketName
                +"/"
                +fileName;

        restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + serviceRoleKey)
                .header("apikey", serviceRoleKey)
                .header("Content-Type", image.getContentType())
                .body(image.getBytes())
                .retrieve()
                .toBodilessEntity();

        return supaBaseUrl
                + "/storage/v1/object/public/"
                +bucketName
                + "/"
                + fileName;
    }


}
