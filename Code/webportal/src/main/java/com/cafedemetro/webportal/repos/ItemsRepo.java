package com.cafedemetro.webportal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cafedemetro.webportal.models.Items;

@Component
public interface ItemsRepo extends JpaRepository<Items, Long> {
    Items findByItemCode(String itemCode);
}
