package com.ssnhealthcare.drugstore.distributor.service.implementation;

import com.ssnhealthcare.drugstore.common.enums.DistributorStatus;
import com.ssnhealthcare.drugstore.common.enums.PurchaseStatus;
import com.ssnhealthcare.drugstore.distributor.dto.request.CreateDistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.request.DistributorRequestDTO;
import com.ssnhealthcare.drugstore.distributor.dto.response.DistributorResponseDTO;
import com.ssnhealthcare.drugstore.distributor.entity.Distributor;
import com.ssnhealthcare.drugstore.exception.DistributorNotFoundException;
import com.ssnhealthcare.drugstore.exception.InvalidDistributorException;
import com.ssnhealthcare.drugstore.distributor.repository.DistributorRepository;
import com.ssnhealthcare.drugstore.distributor.service.DistributorService;
import com.ssnhealthcare.drugstore.purchase.repository.PurchaseOrderRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DistributorServiceImpl implements DistributorService {

   // private DistributorService distributorService;
    private DistributorRepository distributorRepository;
    private PurchaseOrderRepository purchaseOrderRepository;


    @Override
    public DistributorResponseDTO createDistributor(CreateDistributorRequestDTO dto) {

        boolean licenseExists =
                distributorRepository.existsByLicenseNumber(
                        dto.getLicenseNumber());

        if (licenseExists) {
            throw new InvalidDistributorException(
                    "Distributor with this license number already exists");
        }

            Distributor distributor = new Distributor();
            distributor.setDistributorName(dto.getDistributorName());
            distributor.setLicenseNumber(dto.getLicenseNumber());
            distributor.setContactNumber(dto.getContactNumber());
            distributor.setEmail(dto.getEmail());
            distributor.setAddress(dto.getAddress());

            distributor.setStatus(DistributorStatus.ACTIVE);
            Distributor saved =
                    distributorRepository.save(distributor);

            return mapToResponse(saved);
        }


    public DistributorResponseDTO updateDistributor(
            Long distributorId,
            @Valid CreateDistributorRequestDTO dto) {

        Distributor distributor = distributorRepository
                .findById(distributorId)
                .orElseThrow(() ->
                        new DistributorNotFoundException(
                                "Distributor not found with id " + distributorId));


        if (distributor.getStatus() == DistributorStatus.INACTIVE) {
            throw new InvalidDistributorException(
                    "Inactive distributor cannot be updated");
        }

        distributor.setDistributorName(dto.getDistributorName());
        distributor.setContactNumber(dto.getContactNumber());
        distributor.setEmail(dto.getEmail());
        distributor.setAddress(dto.getAddress());

        Distributor updated =
                distributorRepository.save(distributor);

        return mapToResponse(updated);
    }

    @Override
    public DistributorResponseDTO getDistributorById(Long distributorId) {
        Distributor distributor = distributorRepository
                .findById(distributorId)
                .orElseThrow(() ->
                        new DistributorNotFoundException(
                                "Distributor not found with id " + distributorId));



        return mapToResponse(distributor);
    }


    public Page<DistributorResponseDTO> getAllDistributors(DistributorRequestDTO dto){

        Pageable pageable = PageRequest.of(
                dto.getPageNumber(),
                dto.getSize(),
                Sort.by("distributorName").ascending()
        );

        Page<Distributor> distributors =
                distributorRepository.findAll(pageable);

        return distributors.map(this::mapToResponse);
    }

    public DistributorResponseDTO deactivateDistributor(Long id){


        Distributor distributor = distributorRepository
                .findById(id)
                .orElseThrow(() ->
                        new DistributorNotFoundException(
                                "Distributor not found with id " + id));

        if (distributor.getStatus() == DistributorStatus.INACTIVE) {
            throw new InvalidDistributorException(
                    "Distributor is already inactive");
        }

        boolean hasActivePurchases =
                purchaseOrderRepository
                        .existsByDistributorId_DistributorIdAndStatus(
                                id,
                                PurchaseStatus.CREATED);

        if (hasActivePurchases) {
            throw new InvalidDistributorException(
                    "Distributor cannot be deactivated while active purchases exist");
        }

        distributor.setStatus(DistributorStatus.INACTIVE);

        Distributor updated =
                distributorRepository.save(distributor);

        return mapToResponse(updated);

    }



    private DistributorResponseDTO mapToResponse(
            Distributor distributor) {

        DistributorResponseDTO dto =
                new DistributorResponseDTO();

        dto.setDistributorId(distributor.getDistributorId());
        dto.setDistributorName(distributor.getDistributorName());
        dto.setStatus(distributor.getStatus());
        dto.setLicenseNumber(distributor.getLicenseNumber());
        dto.setContactNumber(distributor.getContactNumber());
        dto.setEmail(distributor.getEmail());
        dto.setAddress(distributor.getAddress());

        return dto;
    }
}
