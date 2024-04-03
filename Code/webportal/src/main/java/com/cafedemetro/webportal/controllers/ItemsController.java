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
import com.cafedemetro.webportal.models.CustomException;
import com.cafedemetro.webportal.models.Items;
import com.cafedemetro.webportal.repos.ItemCategoriesRepo;
import com.cafedemetro.webportal.repos.ItemsRepo;
import com.cafedemetro.webportal.utils.ItemsValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private static final String VIEW_PREFIX = "items/";

    @Autowired
    private ItemsRepo repo;

    @Autowired
    private ItemCategoriesRepo categoriesRepo;

    @Autowired
    private ItemsValidator validator;

    @GetMapping({ "", "/", "index" })
    public String index(ModelMap m) {
        m.addAttribute("allItems", repo.findAll());
        return VIEW_PREFIX + "list";
    }

    @GetMapping("/create")
    public String create(ModelMap m) {
        m.addAttribute("items", new Items());
        m.addAttribute("categoriesList", categoriesRepo.findAll());
        return VIEW_PREFIX + "form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, ModelMap m) {
        Optional<Items> oItem = repo.findById(id);
        if (!oItem.isPresent()) {
            return "redirect:/" + VIEW_PREFIX + "index";
        }
        m.addAttribute("items", oItem.get());
        m.addAttribute("categoriesList", categoriesRepo.findAll());
        return VIEW_PREFIX + "form";
    }

    @PostMapping("/formProcess")
    @SuppressWarnings("all")
    public String edit(@Valid @ModelAttribute("items") Items items, BindingResult errors, ModelMap m) {
        validator.validate(items, errors);
        if (errors.hasErrors()) {
            m.addAttribute("items", items);
            m.addAttribute("categoriesList", categoriesRepo.findAll());
            return VIEW_PREFIX + "form";
        }
        repo.save(items);
        return "redirect:/" + VIEW_PREFIX + "index";
    }

    @GetMapping("/delete/{id}")
    @SuppressWarnings("all")
    public String delete(@PathVariable("id") long id, ModelMap m) throws CustomException {
        Optional<Items> oItem = repo.findById(id);
        if (!oItem.isPresent()) {
            return "redirect:/" + VIEW_PREFIX + "index";
        }

        // Check if the item is linked with any orders
        if (!oItem.get().getDetails().isEmpty()) {
            throw new CustomException("-2", "Cannot delete item linked with orders", "/" + VIEW_PREFIX + "index");
        }

        repo.delete(oItem.get());
        return "redirect:/" + VIEW_PREFIX + "index";
    }
}
