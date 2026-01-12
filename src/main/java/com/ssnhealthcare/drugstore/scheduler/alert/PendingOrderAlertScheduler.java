package com.ssnhealthcare.drugstore.scheduler.alert;

import com.ssnhealthcare.drugstore.alert.service.AlertService;
import com.ssnhealthcare.drugstore.sale.entity.Sale;
import com.ssnhealthcare.drugstore.sale.repository.SaleRepository;
import com.ssnhealthcare.drugstore.common.enums.OrderStatus;
import com.ssnhealthcare.drugstore.scheduler.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendingOrderAlertScheduler {

    private final SaleRepository saleRepository;
    private final AlertService alertService;
    private final EmailService emailService;

    @Scheduled(cron = "0 */30 * * * ?")  // every 30 minutes
    public void generatePendingOrderAlerts() {

        List<Sale> pendingOrders =
                saleRepository.findByStatus(OrderStatus.PENDING);

        if (pendingOrders.isEmpty()) return;

        StringBuilder body = new StringBuilder("Pending Orders:\n");

        for (Sale sale : pendingOrders) {
            alertService.createPendingOrderAlert(sale.getSaleId());
            body.append("- Order ID: ")
                    .append(sale.getSaleId()).append("\n");
        }

        emailService.sendPendingOrderEmail(body.toString());
    }
}
