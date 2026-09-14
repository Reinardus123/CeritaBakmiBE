package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.CategoryRequest;
import com.CeritaBakmiBE.CB.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();
    CategoryResponse createCategory(CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(long id);
    void restoreCategory(long id);
    CategoryResponse updateCategory(long categoryId, CategoryRequest categoryRequest);

}
