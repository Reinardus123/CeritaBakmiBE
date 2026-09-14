package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {

    private long categoryId;
    private String categoryName;
    private boolean isActive;
}
