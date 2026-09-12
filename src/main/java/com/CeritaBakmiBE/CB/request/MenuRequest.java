package com.CeritaBakmiBE.CB.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class MenuRequest {

    @NotEmpty(message = "Nama menu harus di isi")
    @Size(min = 3, max = 30, message = "Nama menu minimal 3 karakter")
    private String menuTitle;

    @NotEmpty(message = "deskripsi menu harus di isi")
    @Size(min = 3, max = 30, message = "deskripsi menu minimal 3 karakter")
    private String description;

    @NotEmpty(message = "harga menu harus di isi")
    @Min(1)
    private int price;

    @NotNull(message = "Category harus dipilih")
    private Long categoryId;

    private MultipartFile imagUrl;
}
