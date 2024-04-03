package com.cafedemetro.webportal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.cafedemetro.webportal.models.ItemCategories;

@Component
public interface ItemCategoriesRepo extends JpaRepository<ItemCategories, Long> {
    ItemCategories findByIcCode(String icCode);
}
