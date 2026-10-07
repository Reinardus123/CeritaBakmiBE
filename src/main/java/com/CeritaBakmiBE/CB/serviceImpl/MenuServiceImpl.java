package com.CeritaBakmiBE.CB.serviceImpl;
import com.CeritaBakmiBE.CB.entity.Category;
import com.CeritaBakmiBE.CB.entity.Menu;
import com.CeritaBakmiBE.CB.repository.CategoryRepository;
import com.CeritaBakmiBE.CB.repository.MenuRepository;
import com.CeritaBakmiBE.CB.request.MenuRequest;
import com.CeritaBakmiBE.CB.response.MenuResponse;
import com.CeritaBakmiBE.CB.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository ;
    private final CategoryRepository categoryRepository;
    private final SupabaseStorageService supabaseStorageService;



    @Override
    @Transactional
    public List<MenuResponse> getAllMenu() {
        return menuRepository.findAll()
                .stream()
                .map(menu -> new MenuResponse(
                        menu.getMenuId(),
                        menu.getMenuTitle(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getCategory().getCategoryId(),
                        menu.getImageUrl(),
                        menu.isActive()
                )) .toList();
    }

    @Override
    @Transactional
    public MenuResponse createMenu(MenuRequest menuRequest) throws IOException {

        Category category = categoryRepository.findById(menuRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category tidak ditemukan"));


        Menu menu = new Menu();
        menu.setMenuTitle(menuRequest.getMenuTitle());
        menu.setDescription(menuRequest.getDescription());
        menu.setPrice(menuRequest.getPrice());
        menu.setCategory(category);
        menu.setImageUrl(menuRequest.getImageUrl());

        Menu saveMenu = menuRepository.save(menu);

        return new MenuResponse(
                saveMenu.getMenuId(),
                saveMenu.getMenuTitle(),
                saveMenu.getDescription(),
                saveMenu.getPrice(),
                saveMenu.getCategory().getCategoryId(),
                saveMenu.getImageUrl(),
                saveMenu.isActive()
        );
    }

    @Override
    @Transactional
    public void deleteMenu(long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Menu Not Found"
                        ));

        menu.setActive(!menu.isActive());
        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void restoreMenu(long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Menu not found"
                        ));
        menu.setActive(true);
        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public MenuResponse updateMenu(long id, MenuRequest menuRequest) throws IOException {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Menu Not found"
                        ));
        Category category = categoryRepository.findById(menuRequest.getCategoryId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category Not Found"
                        ));
        menu.setMenuTitle(menuRequest.getMenuTitle());
        menu.setDescription(menuRequest.getDescription());
        menu.setPrice(menuRequest.getPrice());
        menu.setCategory(category);

        if(menuRequest.getImageUrl() != null && !menuRequest.getImageUrl().isBlank()){
            menu.setImageUrl(menuRequest.getImageUrl());
        }
        Menu saveMenu = menuRepository.save(menu);
        return new MenuResponse(
                saveMenu.getMenuId(),
                saveMenu.getMenuTitle(),
                saveMenu.getDescription(),
                saveMenu.getPrice(),
                saveMenu.getCategory().getCategoryId(),
                saveMenu.getImageUrl(),
                saveMenu.isActive()
        );
    }


}
