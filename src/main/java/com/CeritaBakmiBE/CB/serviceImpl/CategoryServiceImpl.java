package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.entity.Category;
import com.CeritaBakmiBE.CB.repository.CategoryRepository;
import com.CeritaBakmiBE.CB.request.CategoryRequest;
import com.CeritaBakmiBE.CB.response.CategoryResponse;
import com.CeritaBakmiBE.CB.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.isActive()
                ))
                .toList();
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) throws Exception {

        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());

        Category saveCategory = categoryRepository.save(category);

        return new CategoryResponse(
                saveCategory.getCategoryId(),
                saveCategory.getCategoryName(),
                saveCategory.isActive()
        );
    }

    @Override
    @Transactional
    public void deleteCategory(long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category Not Found"
                        ));
        category.setActive(false);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void restoreCategory(long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category Not Found"
                        ));
        category.setActive(true);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(long categoryId, CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category Not Found"
                        ));
        category.setCategoryName(categoryRequest.getCategoryName());
        categoryRepository.save(category);

        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.isActive()
        );
    }


}
