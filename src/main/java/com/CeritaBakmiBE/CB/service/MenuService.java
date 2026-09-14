package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.MenuRequest;
import com.CeritaBakmiBE.CB.response.MenuResponse;

import java.io.IOException;
import java.util.List;

public interface MenuService {

    List<MenuResponse> getAllMenu();
    MenuResponse createMenu(MenuRequest menuRequest) throws IOException;
    void deleteMenu(long id);
    void restoreMenu(long id);
    MenuResponse updateMenu(long id, MenuRequest menuRequest) throws IOException;

}
