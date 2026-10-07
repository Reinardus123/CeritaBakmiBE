package com.CeritaBakmiBE.CB.controller;


import com.CeritaBakmiBE.CB.request.MenuRequest;
import com.CeritaBakmiBE.CB.response.MenuResponse;
import com.CeritaBakmiBE.CB.response.SignedUploadResponse;
import com.CeritaBakmiBE.CB.service.MenuService;
import com.CeritaBakmiBE.CB.serviceImpl.SupabaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Tag(name = "Menu Rest API Endpoints", description = "CRUD operations")
public class MenuController {

    private final MenuService menuService;
    private final SupabaseStorageService supabaseStorageService;

    public MenuController(MenuService menuService, SupabaseStorageService supabaseStorageService) {
        this.menuService = menuService;
        this.supabaseStorageService = supabaseStorageService;
    }

    @Operation(summary = "Create Menu", description = "Create new menu")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/createMenu")
    public MenuResponse createMenu(@Valid @RequestBody  MenuRequest menuRequest) throws IOException {
        return menuService.createMenu(menuRequest);
    }

    @Operation(summary = "Soft Delete Menu", description = "Delete Menu")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<Void> deleteMenu(@Valid @PathVariable long id){
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get All Menu", description = "Get all menu")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getMenu")
    public List<MenuResponse>getAllMenu(){
        return menuService.getAllMenu();
    }

    @Operation(summary = "Restore menu", description = "Restore menu")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Void> restoreMenu(@Valid @PathVariable long id){
        menuService.restoreMenu(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update menu", description = "update menu")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/update/{id}")
    public MenuResponse updateMenu(@PathVariable long id, @Valid @RequestBody MenuRequest menuRequest) throws IOException{
        return menuService.updateMenu(id, menuRequest);
    }

    @PostMapping("/upload-url")
    public SignedUploadResponse createUploadUrl(@RequestParam String fileName){
        return supabaseStorageService.createSignedUploadUrl(fileName);
    }


}
