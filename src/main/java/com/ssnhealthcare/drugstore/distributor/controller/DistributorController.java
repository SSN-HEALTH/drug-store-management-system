package com.ssnhealthcare.drugstore.distributor.controller;

import com.ssnhealthcare.drugstore.distributor.dto.request.CreateDistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.request.DistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.response.DistributorResponseDTO;
import com.ssnhealthcare.drugstore.distributor.service.DistributorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DistributorController {

    private DistributorService distributorService;

    @PostMapping("/api/distributors")
    public ResponseEntity <DistributorResponseDTO> createDistributor(@Valid @RequestBody CreateDistributorRequestDTO dto){

        DistributorResponseDTO response = distributorService.createDistributor(dto);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/api/distributors/{id}")
    public ResponseEntity <DistributorResponseDTO> updateDistributor(@Valid @RequestBody Long id, DistributorRequestDTO dto) {

        DistributorResponseDTO response = distributorService.updateDistributor(id, dto);
        return ResponseEntity.ok(response);

    }

    @GetMapping ("/api/distributors/{id}")
    public ResponseEntity <DistributorResponseDTO> getDistributorById(@Valid @RequestBody Long id) {

        DistributorResponseDTO response = distributorService.getDistributorById(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping ("/api/distributors")
    public ResponseEntity<Page<DistributorResponseDTO>>
    listDistributors(@Valid @RequestBody DistributorRequestDTO dto) {

        Page<DistributorResponseDTO> response = distributorService.getAllDistributors(dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/api/distributors/{id}/deactivate")
    public ResponseEntity<DistributorResponseDTO>
    deactivateDistributor(@PathVariable Long id) {

        DistributorResponseDTO response = distributorService.deactivateDistributor(id);

        return ResponseEntity.ok(response);
    }
}
