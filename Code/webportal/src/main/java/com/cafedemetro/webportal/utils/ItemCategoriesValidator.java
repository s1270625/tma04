package com.cafedemetro.webportal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.cafedemetro.webportal.models.ItemCategories;
import com.cafedemetro.webportal.repos.ItemCategoriesRepo;

@Component
public class ItemCategoriesValidator implements Validator {

    @Autowired
    private ItemCategoriesRepo repo;

    @Override
    @SuppressWarnings("all")
    public boolean supports(Class<?> clazz) {
        return ItemCategories.class.equals(clazz);
    }

    @Override
    @SuppressWarnings("all")
    public void validate(Object target, Errors errors) {
        ItemCategories branch = (ItemCategories) target;

        // check whether the icCode of a new item category already exists
        // or whether the icCode of an existing item category has changed but same as
        // another existing item category icCode
        ItemCategories existingBranch = repo.findByIcCode(branch.getIcCode());
        if (existingBranch != null && (branch.getIcId() == 0 || existingBranch.getIcId() != branch.getIcId())) {
            errors.rejectValue("icCode", "", "Item category code already exists");
        }
    }

}
