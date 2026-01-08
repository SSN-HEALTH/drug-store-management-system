package com.ssnhealthcare.drugstore.drug.service;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.CategoryRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllCategoriesRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.CategoryResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService{
    CategoryResponseDTO addCategory(CategoryRequestDTO dto);

    CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO dto);

    CategoryResponseDTO getCategoryById(Long categoryId);

    Page<CategoryResponseDTO> getAllCategories(GetAllCategoriesRequestDTO dto);

    void deleteCategory(Long categoryId);
}
