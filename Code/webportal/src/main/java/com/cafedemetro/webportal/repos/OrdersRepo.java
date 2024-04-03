package com.cafedemetro.webportal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cafedemetro.webportal.models.Orders;

@Component
public interface OrdersRepo extends JpaRepository<Orders, Long> {
    Orders findByQrCode(String qrCode);
}
