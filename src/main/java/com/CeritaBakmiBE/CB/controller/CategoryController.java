package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.CategoryRequest;
import com.CeritaBakmiBE.CB.response.CategoryResponse;
import com.CeritaBakmiBE.CB.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Category REST API", description = "Category CRUD Operations")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create category", description = "Create category for menu")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createCat")
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest) throws Exception {
        return categoryService.createCategory(categoryRequest);
    }

    @Operation(summary = "Get all Categories", description = "Get All Categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCat")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Soft delete Category", description = "Soft Delete Category")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Restore category status", description = "Restore category status")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public ResponseEntity<Void> restoreCategory(@Valid @PathVariable long id){
        categoryService.restoreCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update category name", description = "Update category name")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/category/{id}")
    public CategoryResponse updateCategory(@PathVariable long id, @Valid @RequestBody CategoryRequest categoryRequest ){
        return categoryService.updateCategory(id , categoryRequest);
    }


}
