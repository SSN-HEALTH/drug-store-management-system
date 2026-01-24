package com.ssnhealthcare.drugstore.scheduler.alert;

import com.ssnhealthcare.drugstore.alert.service.AlertService;
import com.ssnhealthcare.drugstore.inventory.entity.Inventory;
import com.ssnhealthcare.drugstore.inventory.repository.InventoryRepository;
import com.ssnhealthcare.drugstore.scheduler.email.AlertRecipientService;
import com.ssnhealthcare.drugstore.scheduler.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DailyStockAlertScheduler {

    private final InventoryRepository inventoryRepository;
    private final AlertService alertService;
    private final EmailService emailService;
    private final AlertRecipientService recipientService;

    @Scheduled(cron = "0 0 0/1 * * ?")  // every 1 hour
    public void generateDailyAlerts() {

        LocalDate today = LocalDate.now();
        StringBuilder body = new StringBuilder();
        boolean hasNewAlerts = false;

        for (Inventory inv : inventoryRepository.findExpired(today)) {
            if (alertService.createExpiredAlert(
                    inv.getDrug().getDrugId(),
                    inv.getDrug().getDrugName(),
                    inv.getExpiryDate())) {

                hasNewAlerts = true;
                body.append("EXPIRED: ")
                        .append(inv.getDrug().getDrugName())
                        .append("\n");
            }
        }

        for (Inventory inv : inventoryRepository.findNearExpiry(
                today.plusDays(1), today.plusDays(30))) {

            if (alertService.createNearExpiryAlert(
                    inv.getDrug().getDrugId(),
                    inv.getDrug().getDrugName(),
                    inv.getExpiryDate())) {

                hasNewAlerts = true;
                body.append("NEAR EXPIRY: ")
                        .append(inv.getDrug().getDrugName())
                        .append("\n");
            }
        }

        for (Inventory inv : inventoryRepository.findLowStock(today)) {
            if (alertService.createLowStockAlert(
                    inv.getDrug().getDrugId(),
                    inv.getDrug().getDrugName(),
                    inv.getQuantity())) {

                hasNewAlerts = true;
                body.append("LOW STOCK: ")
                        .append(inv.getDrug().getDrugName())
                        .append("\n");
            }
        }

        if (hasNewAlerts) {
            List<String> recipients = recipientService.getAlertRecipients();
            emailService.sendStockAlertEmail(recipients, body.toString());
            log.info("Stock alert mail sent to admins & stock managers");
        }
    }
}
