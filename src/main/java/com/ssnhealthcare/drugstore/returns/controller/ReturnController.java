package com.ssnhealthcare.drugstore.returns.controller;

import com.ssnhealthcare.drugstore.returns.dto.requestdto.CreateReturnRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.requestdto.ReturnPageRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import com.ssnhealthcare.drugstore.returns.service.ReturnService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/return")
@AllArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

 @PostMapping("createReturn")
 public ResponseEntity<ReturnResponseDTO> createReturn(@Valid @RequestBody
                                       CreateReturnRequestDTO dto){
     ReturnResponseDTO response = returnService.createReturn(dto);

     return ResponseEntity.status(HttpStatus.CREATED)
                          .body(response);
 }

    @GetMapping("getAll")
    public ResponseEntity<Page<ReturnResponseDTO>> getAllReturns(
            @Valid ReturnPageRequestDTO dto) {
        return ResponseEntity.ok(returnService.getAllReturns(dto));
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<ReturnResponseDTO> getReturnById(
            @PathVariable Long id) {

        return ResponseEntity.ok(returnService.getReturnById(id));
    }
}
