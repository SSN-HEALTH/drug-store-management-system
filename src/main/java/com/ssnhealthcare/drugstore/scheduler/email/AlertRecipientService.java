package com.ssnhealthcare.drugstore.scheduler.email;

import com.ssnhealthcare.drugstore.common.enums.RoleType;
import com.ssnhealthcare.drugstore.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertRecipientService {

    private final EmployeeRepository employeeRepository;

    public List<String> getAlertRecipients() {
        return employeeRepository.findEmailsByRoles(
                List.of(RoleType.ADMIN, RoleType.STOCK_MANAGER)
        );
    }
}