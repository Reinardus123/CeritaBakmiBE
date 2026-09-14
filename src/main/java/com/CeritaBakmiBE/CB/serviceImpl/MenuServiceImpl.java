package com.CeritaBakmiBE.CB.serviceImpl;
import com.CeritaBakmiBE.CB.entity.Category;
import com.CeritaBakmiBE.CB.entity.Menu;
import com.CeritaBakmiBE.CB.repository.CategoryRepository;
import com.CeritaBakmiBE.CB.repository.MenuRepository;
import com.CeritaBakmiBE.CB.request.MenuRequest;
import com.CeritaBakmiBE.CB.response.MenuResponse;
import com.CeritaBakmiBE.CB.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository ;
    private final CategoryRepository categoryRepository;

    public MenuServiceImpl(MenuRepository menuRepository, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }

    private String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID()
                + "_"
                +image.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(
                image.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return "/uploads/" + fileName;
    }

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

        MultipartFile image = menuRequest.getImage();
        String imageUrl = saveImage(image);

        Menu menu = new Menu();
        menu.setMenuTitle(menuRequest.getMenuTitle());
        menu.setDescription(menuRequest.getDescription());
        menu.setPrice(menuRequest.getPrice());
        menu.setCategory(category);
        menu.setImageUrl(imageUrl);

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

        menu.setActive(false);
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
        if(menuRequest.getImage() != null && !menuRequest.getImage().isEmpty()){
            String imageUrl = saveImage(menuRequest.getImage());
            menu.setImageUrl(imageUrl);
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
