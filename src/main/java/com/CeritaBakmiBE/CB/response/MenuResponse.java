package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MenuResponse {

    private long menuId;
    private String MenuTitle;
    private String description;
    private int price;
    private long categoryId;
    private String ImageUrl;
    private boolean isActive;

}
