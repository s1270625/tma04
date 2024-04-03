package com.cafedemetro.webportal.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.cafedemetro.webportal.models.Branches;
import com.cafedemetro.webportal.repos.BranchesRepo;

@Component
/* 1. implements from the org.springframework.validation.Validator interface */
public class BranchesValidator implements Validator {

    // 2. dependency injection the JpaRepository of Branches
    @Autowired
    private BranchesRepo repo;

    @Override
    @SuppressWarnings("all")
    public boolean supports(Class<?> clazz) {
        return Branches.class.equals(clazz);
    }

    @Override
    @SuppressWarnings("all")
    public void validate(Object target, Errors errors) {
        Branches branch = (Branches) target;

        // 2. check whether the branchCode of a new branches already exists
        // or whether the branchCode of an existing branches has changed but same as
        // another existing branches branchCode
        Branches existingBranch = repo.findByBranchCode(branch.getBranchCode());
        // 3. if the branchCode exists, reject the request with an error message "Branch
        // code already exists" on field "branchCode" and with error code ""
        if (existingBranch != null && (existingBranch.getBId() != branch.getBId() || branch.getBId() == 0)) {
            errors.rejectValue("branchCode", "", "Branch code already exists");
        }
    }// end of validate()
}