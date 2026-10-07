package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.response.SignedUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
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


    public SignedUploadResponse createSignedUploadUrl(String fileName){

        String bucketName = "menu-images";

        String path = UUID.randomUUID() + "_" + fileName;

        String url = supaBaseUrl
                + "/storage/v1/object/upload/sign/"
                + bucketName
                + "/"
                + path;

        String response = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + serviceRoleKey)
                .header("apikey",serviceRoleKey)
                .retrieve()
                .body(String.class);

        return new SignedUploadResponse(
                path,
                extractToken(response)
        );
    }

    private String extractToken(String response){

        int tokenIndex = response.indexOf("token=");

        if(tokenIndex == -1){
            throw new RuntimeException("Token tidak ditemukan di supabase");
        }

        String token = response.substring(tokenIndex + 6);

        int endIndex = token.indexOf("\"");

        if(endIndex != -1){
            token = token.substring(0, endIndex);
        }

        return token;
    }






}
