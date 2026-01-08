package com.ssnhealthcare.drugstore.returns.service;

import com.ssnhealthcare.drugstore.returns.dto.requestdto.CreateReturnRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.requestdto.ReturnPageRequestDTO;
import com.ssnhealthcare.drugstore.returns.dto.responsedto.ReturnResponseDTO;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

public interface ReturnService {
    ReturnResponseDTO createReturn(@Valid CreateReturnRequestDTO dto);

    Page<ReturnResponseDTO> getAllReturns(@Valid ReturnPageRequestDTO dto);

     ReturnResponseDTO getReturnById(Long id);
}
