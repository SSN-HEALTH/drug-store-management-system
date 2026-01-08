package com.ssnhealthcare.drugstore.distributor.service;

import com.ssnhealthcare.drugstore.distributor.dto.request.CreateDistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.request.DistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.response.DistributorResponseDTO;
import org.springframework.data.domain.Page;


public interface DistributorService {

    DistributorResponseDTO createDistributor(CreateDistributorRequestDTO dto);
    DistributorResponseDTO updateDistributor(Long distributorId, DistributorRequestDTO dto);
    DistributorResponseDTO getDistributorById(Long distributorId);
    Page<DistributorResponseDTO> getAllDistributors(DistributorRequestDTO dto);

    DistributorResponseDTO deactivateDistributor(Long id);
}
