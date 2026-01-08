package com.ssnhealthcare.drugstore.drug.service.implementation;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.CategoryRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllCategoriesRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.CategoryResponseDTO;
import com.ssnhealthcare.drugstore.drug.entity.Category;
import com.ssnhealthcare.drugstore.drug.repository.CategoryRepository;
import com.ssnhealthcare.drugstore.drug.service.CategoryService;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.exception.CategoryNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO addCategory(CategoryRequestDTO dto) {

        if (categoryRepository.existsByName(dto.getName())) {
            throw new BusinessException("Category already exists with name: " + dto.getName());
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO dto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + categoryId));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + categoryId));

        return mapToResponse(category);
    }

    @Override
    public Page<CategoryResponseDTO> getAllCategories(GetAllCategoriesRequestDTO dto) {

        Pageable pageable = PageRequest.of(dto.getPageNumber()
                , dto.getSize(), Sort.by("categoryName").ascending());

        return categoryRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + categoryId));

        categoryRepository.delete(category);
    }

    private CategoryResponseDTO mapToResponse(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
