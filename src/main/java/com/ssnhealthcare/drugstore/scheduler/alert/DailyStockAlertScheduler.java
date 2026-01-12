package com.ssnhealthcare.drugstore.scheduler.alert;

import com.ssnhealthcare.drugstore.alert.service.AlertService;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.scheduler.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyStockAlertScheduler {

    private final InventoryRepository inventoryRepository;
    private final AlertService alertService;
    private final EmailService emailService;

    private static final int NEAR_EXPIRY_DAYS = 30;

    @Scheduled(cron = "0 0 9 * * ?")   // every day 9 am
    public void generateDailyAlerts() {

        StringBuilder emailBody = new StringBuilder();
        boolean sendMail = false;

        // low stock

        List<Inventory> lowStock = inventoryRepository.findLowStock();
        for (Inventory inv : lowStock) {
            alertService.createLowStockAlert(
                    inv.getDrug().getDrugId(),
                    inv.getDrug().getDrugName(),
                    inv.getQuantity()
            );
            sendMail = true;
            emailBody.append("LOW STOCK: ")
                    .append(inv.getDrug().getDrugName())
                    .append(" | Qty: ")
                    .append(inv.getQuantity()).append("\n");
        }
        // near expiry

        LocalDate nearExpiryDate = LocalDate.now().plusDays(NEAR_EXPIRY_DAYS);
        List<Inventory> nearExpiry =
                inventoryRepository.findByExpiryDateBeforeAndQuantityGreaterThan(
                        nearExpiryDate, 0
                );

        for (Inventory inv : nearExpiry) {
            alertService.createNearExpiryAlert(
                    inv.getDrug().getDrugId(),
                    inv.getDrug().getDrugName(),
                    inv.getExpiryDate()
            );
            sendMail = true;
            emailBody.append("NEAR EXPIRY: ")
                    .append(inv.getDrug().getDrugName())
                    .append(" | ")
                    .append(inv.getExpiryDate()).append("\n");
        }

        if (sendMail) {
            emailService.sendStockAlertEmail(emailBody.toString());
        }
    }
}
