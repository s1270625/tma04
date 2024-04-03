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
import com.cafedemetro.webportal.models.ItemCategories;
import com.cafedemetro.webportal.repos.ItemCategoriesRepo;
import com.cafedemetro.webportal.utils.ItemCategoriesValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/itemCategories")
public class ItemCategoriesController {
    private static final String VIEW_PREFIX = "itemCategories/";

    @Autowired
    private ItemCategoriesRepo repo;

    @Autowired
    private ItemCategoriesValidator validator;

    @GetMapping({ "", "/", "index" })
    public String index(ModelMap m) {
        m.addAttribute("allItemCategories", repo.findAll());
        return VIEW_PREFIX + "list";
    }

    @GetMapping("/create")
    public String create(ModelMap m) {
        m.addAttribute("itemCategory", new ItemCategories());
        return VIEW_PREFIX + "form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, ModelMap m) throws CustomException {
        Optional<ItemCategories> oItemCategory = repo.findById(id);
        if (!oItemCategory.isPresent()) {
            throw new CustomException("-1", "ItemCategory not found", "/" + VIEW_PREFIX + "index");
        }
        m.addAttribute("itemCategory", oItemCategory.get());
        return VIEW_PREFIX + "form";
    }

    @PostMapping("/formProcess")
    @SuppressWarnings("all")
    public String edit(@Valid @ModelAttribute("itemCategory") ItemCategories itemCategory,
            BindingResult errors, ModelMap m) {
        validator.validate(itemCategory, errors);
        if (errors.hasErrors()) {
            m.addAttribute("itemCategory", itemCategory);
            return VIEW_PREFIX + "form";
        }
        repo.save(itemCategory);
        return "redirect:/" + VIEW_PREFIX + "index";
    }

    @GetMapping("/delete/{id}")
    @SuppressWarnings("all")
    public String delete(@PathVariable("id") long id, ModelMap m) throws CustomException {
        Optional<ItemCategories> oItemCategory = repo.findById(id);
        if (!oItemCategory.isPresent()) {
            throw new CustomException("-1", "ItemCategory not found", "/" + VIEW_PREFIX + "index");
        }
        // if there are items linked with this item category, cannot delete
        if (!oItemCategory.get().getItems().isEmpty()) {
            throw new CustomException("-2", "Cannot delete item category with linked items",
                    "/" + VIEW_PREFIX + "index");
        }
        repo.delete(oItemCategory.get());
        return "redirect:/" + VIEW_PREFIX + "index";
    }
}