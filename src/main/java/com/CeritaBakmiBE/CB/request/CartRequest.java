package com.CeritaBakmiBE.CB.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartRequest {

    @NotNull(message = "silahkan pilih menu")
    private Long menuId;

    @NotNull
    private int quantity;

}
