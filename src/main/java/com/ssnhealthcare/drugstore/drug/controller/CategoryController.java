package com.ssnhealthcare.drugstore.drug.controller;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.CategoryRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllCategoriesRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.CategoryResponseDTO;
import com.ssnhealthcare.drugstore.drug.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryResponseDTO> addCategory(
            @Valid @RequestBody CategoryRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.addCategory(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO dto) {

        return ResponseEntity.ok(categoryService.updateCategory(id, dto));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @Valid @RequestBody GetAllCategoriesRequestDTO dto
            ) {
        return ResponseEntity.ok(categoryService.getAllCategories(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
