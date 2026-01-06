package com.ssnhealthcare.drugstore.drug.service;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.AddDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllDrugsRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.UpdateDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.DrugResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;


public interface DrugService {
    DrugResponseDTO addDrug(@Valid AddDrugRequestDTO dto);

    DrugResponseDTO updateDrug(@Valid UpdateDrugRequestDTO dto);

    void deleteDrug(Long id);

    DrugResponseDTO getDrugById(Long id);

    Page<DrugResponseDTO> getAllDrugs(@Valid GetAllDrugsRequestDTO dto);
}
