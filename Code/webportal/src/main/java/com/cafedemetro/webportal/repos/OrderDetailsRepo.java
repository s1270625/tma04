package com.cafedemetro.webportal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cafedemetro.webportal.models.OrderDetails;

@Component
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

}
