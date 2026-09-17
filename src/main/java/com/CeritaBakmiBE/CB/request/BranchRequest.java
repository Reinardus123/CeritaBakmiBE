package com.CeritaBakmiBE.CB.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Getter
@Setter
public class BranchRequest {

    @NotNull(message = "Cabang tidak boleh kosong")
    private String BranchName;

    @NotNull(message = "Alamat cabang tidak boleh kosong")
    private String Address;



}
