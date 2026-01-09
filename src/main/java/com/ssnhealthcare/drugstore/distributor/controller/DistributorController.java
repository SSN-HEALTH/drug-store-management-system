package com.ssnhealthcare.drugstore.distributor.controller;

import com.ssnhealthcare.drugstore.distributor.dto.request.CreateDistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.request.DistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.response.DistributorResponseDTO;
import com.ssnhealthcare.drugstore.distributor.service.DistributorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/distributor")
public class DistributorController {

    private DistributorService distributorService;

    @PostMapping("/createDistributor")
    public ResponseEntity <DistributorResponseDTO> createDistributor(@Valid @RequestBody CreateDistributorRequestDTO dto){

        DistributorResponseDTO response = distributorService.createDistributor(dto);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/updateDistributor/{id}")
    public ResponseEntity <DistributorResponseDTO> updateDistributor(@PathVariable Long id,
                                                     @Valid @RequestBody CreateDistributorRequestDTO dto) {

        DistributorResponseDTO response = distributorService.updateDistributor(id, dto);
        return ResponseEntity.ok(response);

    }

    @GetMapping ("/getById/{id}")
    public ResponseEntity <DistributorResponseDTO> getDistributorById(@PathVariable Long id) {

        DistributorResponseDTO response = distributorService.getDistributorById(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping ("/getAll")
    public ResponseEntity<Page<DistributorResponseDTO>>
    listDistributors(@Valid @RequestBody DistributorRequestDTO dto) {

        Page<DistributorResponseDTO> response = distributorService.getAllDistributors(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/deactivate")
    public ResponseEntity<DistributorResponseDTO>
    deactivateDistributor(@PathVariable Long id) {

        DistributorResponseDTO response =
                distributorService.deactivateDistributor(id);

        return ResponseEntity.ok(response);
    }
}
