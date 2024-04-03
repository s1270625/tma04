package com.cafedemetro.webportal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.cafedemetro.webportal.models.Items;
import com.cafedemetro.webportal.repos.ItemsRepo;
import org.springframework.stereotype.Component;

@Component
public class ItemsValidator implements Validator {

    @Autowired
    private ItemsRepo repo;

    @Override
    @SuppressWarnings("all")
    public boolean supports(Class<?> clazz) {
        return Items.class.equals(clazz);
    }

    @Override
    @SuppressWarnings("all")
    public void validate(Object target, Errors errors) {
        Items item = (Items) target;
        // check whether the itemCode of a new Items is the same as an existing Items
        // or the itemCode of an edited Items is the same as another existing Items
        // itemCode
        Items existingItem = repo.findByItemCode(item.getItemCode());
        if (existingItem != null && (item.getItemId() == 0 || existingItem.getItemId() != item.getItemId())) {
            errors.rejectValue("itemCode", "", "Item code already exists");
        }
    }

}
