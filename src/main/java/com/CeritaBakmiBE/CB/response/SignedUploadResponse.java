package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignedUploadResponse {

    private String path;
    private String token;
}
