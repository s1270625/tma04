package com.cafedemetro.webportal.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cafedemetro.webportal.models.Branches;
import com.cafedemetro.webportal.models.CustomException;
import com.cafedemetro.webportal.repos.BranchesRepo;
import com.cafedemetro.webportal.utils.BranchesValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/branches")
public class BranchesController {

    private static final String VIEW_PREFIX = "branches/";

    // 2. dependency injection the JpaRepository of Branches
    @Autowired
    private BranchesRepo repo;

    // 3. dependency injection the validator of Branches
    @Autowired
    private BranchesValidator validator;

    // 4. implement the HTTP GET method with the path "/", "" or "index"
    @GetMapping({ "", "/", "index" })
    public String index(ModelMap m) {
        // 5. retrieve all branches record from DB and send to view with key
        // "allBranches"
        m.addAttribute("allBranches", repo.findAll());
        return VIEW_PREFIX + "list";
    }

    // 4. implement the HTTP GET method with the path "/create"
    @GetMapping("/create")
    public String create(ModelMap m) {
        // 5. create a new Branches object and send to view with key "branches"
        m.addAttribute("branches", new Branches());
        return VIEW_PREFIX + "form";
    }

    // 6. implement the HTTP GET method with the path "/edit/{id}"
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") /* 7. extract the id from the path variable */ long id, ModelMap m)
            throws CustomException {
        // 8. retrieve the Branches record from DB by id, beware that the return is
        // optional
        Optional<Branches> oBranch = repo.findById(id);
        // 9. check if the Branches record exists, if not, throw a CustomException with
        // errCode = 1, errMsg = "Branch not found", and redirect to "/"
        if (!oBranch.isPresent()) {
            throw new CustomException("-1", "Branch not found", "/" + VIEW_PREFIX + "index");
        }
        // 10. Get the Branches object from the optional return from repo, pass it to
        // view with key "branches"
        m.addAttribute("branches", oBranch.get());
        return VIEW_PREFIX + "form";
    }

    // 11. implement the HTTP POST method with the path "/formProcess"
    @PostMapping("/formProcess")
    @SuppressWarnings("all")
    public String create(
            @Valid /* 12. validate the Branches object with an specific annotation */
            @ModelAttribute("branches") /* 13. extract the Branches object from the form with key "branches" */ Branches branches,
            BindingResult errors /* 14. There should be a parameter to hold the binding result of validation */,
            ModelMap m) {
        /* 15. validate the Branches object with the validator */
        validator.validate(branches, errors);
        // 16. check if the validation failed, if so, send to view VIEW_PREFIX + "form"
        // with key "branches"
        if (errors.hasErrors()) {
            m.addAttribute("branches", branches);
            return VIEW_PREFIX + "form";
        }
        // 17. save the Branches object to DB
        repo.save(branches);
        // 18. redirect to "/" + VIEW_PREFIX + "index";
        return "redirect:/" + VIEW_PREFIX + "index";
    }

    // 19. implement the HTTP GET method with the path "/delete/{id}"
    @GetMapping("/delete/{id}")
    @SuppressWarnings("all")
    public String delete(@PathVariable("id") /* 20. extract the id from the path variable */ long id, ModelMap m)
            throws CustomException {
        // 21. retrieve the Branches record from DB by id, beware that the return is
        // optional
        Optional<Branches> oBranch = repo.findById(id);
        // 22. check if the Branches record exists, if not, throw a CustomException with
        // errCode = 1, errMsg = "Branch not found", and redirect to "/" + VIEW_PREFIX +
        // "index"
        if (!oBranch.isPresent()) {
            throw new CustomException("-1", "Branch not found", "/" + VIEW_PREFIX + "index");
        }

        // 23. if there are orders linked with this branch, cannot delete. Throw a
        // CustomException with errCode = 2, errMsg = "Cannot delete branch with linked
        // orders", and redirect to "/" + VIEW_PREFIX + "index"
        if (!oBranch.get().getOrders().isEmpty()) {
            throw new CustomException("-2", "Cannot delete branch with linked orders", "/" + VIEW_PREFIX + "index");
        }

        // 24. delete the Branches record from DB
        repo.delete(oBranch.get());
        // 25. redirect to "/" + VIEW_PREFIX + "index"
        return "redirect:/" + VIEW_PREFIX + "index";
    }
}