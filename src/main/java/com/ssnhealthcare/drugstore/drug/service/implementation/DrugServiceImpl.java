package com.ssnhealthcare.drugstore.drug.service.implementation;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.AddDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllDrugsRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.UpdateDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.DrugResponseDTO;
import com.ssnhealthcare.drugstore.drug.entity.Category;
import com.ssnhealthcare.drugstore.drug.entity.Drug;
import com.ssnhealthcare.drugstore.drug.repository.CategoryRepository;
import com.ssnhealthcare.drugstore.drug.repository.DrugRepository;
import com.ssnhealthcare.drugstore.drug.service.DrugService;
import com.ssnhealthcare.drugstore.exception.BusinessException;
import com.ssnhealthcare.drugstore.exception.CategoryNotFoundException;
import com.ssnhealthcare.drugstore.exception.DrugNotFoundException;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public DrugResponseDTO addDrug(AddDrugRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found for the given id: " + dto.getCategoryId()));

        Drug drug = new Drug();

        drug.setDrugName(dto.getDrugName());
        drug.setComposition(dto.getComposition());
        drug.setDosage(dto.getDosage());
        drug.setManufacturer(dto.getManufacturer());
        drug.setCategory(category);

        Drug savedDrug = drugRepository.save(drug);

        return mapToResponseDto(savedDrug);
    }

    @Override
    public DrugResponseDTO updateDrug(UpdateDrugRequestDTO dto) {

        Drug drug = drugRepository.findById(dto.getId())
                .orElseThrow(() -> new DrugNotFoundException("Drug not found for the given id: "
                        + dto.getId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found for the given id: "
                        + dto.getCategoryId()));

        drug.setDrugName(dto.getDrugName());
        drug.setComposition(dto.getComposition());
        drug.setDosage(dto.getDosage());
        drug.setManufacturer(dto.getManufacturer());
        drug.setCategory(category);

         Drug update = drugRepository.save(drug);
        return mapToResponseDto(update);
    }

    @Override
    @Transactional
    public void deleteDrug(Long id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() ->new DrugNotFoundException("Drug not found for the given id: "
                + id));

        boolean inventoryExists = inventoryRepository.existsByDrug(drug);

        if (inventoryExists) {
            throw new BusinessException(
                    "Cannot delete drug with existing inventory"
            );
        }
        drugRepository.delete(drug);
    }

    @Override
    public DrugResponseDTO getDrugById(Long id) {

        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new DrugNotFoundException("Drug not found for the given id: "
                        + id));

        return mapToResponseDto(drug);
    }

    @Override
    public Page<DrugResponseDTO> getAllDrugs(GetAllDrugsRequestDTO dto) {

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("drugName").ascending());

        Page<Drug> drugPage = drugRepository.findAll(pageable);
        return drugPage.map(this::mapToResponseDto);
    }

    public DrugResponseDTO mapToResponseDto(Drug drug){
        DrugResponseDTO response = new DrugResponseDTO();

        response.setDrugId(drug.getDrugId());
        response.setDrugName(drug.getDrugName());
        response.setComposition(drug.getComposition());
        response.setDosage(drug.getDosage());
        response.setManufacturer(drug.getManufacturer());
        response.setCategoryName(drug.getCategory().getName());
        return response;
    }
}
