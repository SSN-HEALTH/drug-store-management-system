package com.ssnhealthcare.drugstore.drug.controller;

import com.ssnhealthcare.drugstore.drug.dto.requestdto.AddDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.GetAllDrugsRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.requestdto.UpdateDrugRequestDTO;
import com.ssnhealthcare.drugstore.drug.dto.responsedto.DrugResponseDTO;
import com.ssnhealthcare.drugstore.drug.service.DrugService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drug")
@AllArgsConstructor
public class DrugController {

    private final DrugService drugService;

 @PostMapping("/addDrug")
 public ResponseEntity<DrugResponseDTO> addDrug(@Valid @RequestBody AddDrugRequestDTO dto){
     DrugResponseDTO response = drugService.addDrug(dto);
     return ResponseEntity.status(HttpStatus.CREATED).body(response);
 }

@PutMapping("/updateDrug")
public ResponseEntity<DrugResponseDTO> updateDrug(@Valid @RequestBody UpdateDrugRequestDTO dto) {

    DrugResponseDTO response = drugService.updateDrug(dto);
    return ResponseEntity.ok(response);
}

 @DeleteMapping("/delete/{id}")
 public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
     drugService.deleteDrug(id);
     return ResponseEntity.noContent().build();
    }

@GetMapping("/getById/{id}")
public ResponseEntity<DrugResponseDTO> getDrugById(@PathVariable Long id) {
    return ResponseEntity.ok(drugService.getDrugById(id));
}

@GetMapping("/getByAll")
public ResponseEntity<Page<DrugResponseDTO>> getAllDrugs(
        @Valid GetAllDrugsRequestDTO dto) {

    Page<DrugResponseDTO> response = drugService.getAllDrugs(dto);
    return ResponseEntity.ok(response);
}

}
