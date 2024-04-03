package com.cafedemetro.webportal.controllers;

import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cafedemetro.webportal.models.CustomException;
import com.cafedemetro.webportal.models.OrderDetailForm;
import com.cafedemetro.webportal.models.OrderDetails;
import com.cafedemetro.webportal.models.Orders;
import com.cafedemetro.webportal.repos.ItemCategoriesRepo;
import com.cafedemetro.webportal.repos.ItemsRepo;
import com.cafedemetro.webportal.repos.OrderDetailsRepo;
import com.cafedemetro.webportal.repos.OrdersRepo;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "/", "orders", })
public class OrdersController {

    private static final String VIEW_PREFIX = "orders/";

    @Autowired
    private OrdersRepo repo;

    @Autowired
    private OrderDetailsRepo detailsRepo;

    @Autowired
    private ItemsRepo itemsRepo;

    @Autowired
    private ItemCategoriesRepo categoriesRepo;

    @GetMapping({ "", "/", "/index" })
    public String index(HttpSession session) {
        String qrCode = (String) session.getAttribute("qrCode");
        if (qrCode != null) {
            return "redirect:/" + VIEW_PREFIX + "takeOrder" + "?qrCode=" + qrCode;
        }
        return VIEW_PREFIX + "index";
    }

    @GetMapping("/takeOrder")
    public String takeOrder(@RequestParam("qrCode") String qrCode, ModelMap m, HttpSession session)
            throws CustomException {
        if (repo.findByQrCode(qrCode) == null) {
            throw new CustomException("-3", "Qr Code not found", "/" + VIEW_PREFIX + "index");
        }
        session.setAttribute("qrCode", qrCode);
        m.addAttribute("allCategories", categoriesRepo.findAll());
        m.addAttribute("allItems", itemsRepo.findAll());
        m.addAttribute("itemsQty", new OrderDetailForm());
        return VIEW_PREFIX + "takeOrder";
    }

    @Transactional
    @PostMapping("/takeOrder")
    public String takeOrder(@ModelAttribute("itemsQty") OrderDetailForm details, HttpSession session)
            throws CustomException {
        String qrCode = (String) session.getAttribute("qrCode");

        Orders order = repo.findByQrCode(qrCode);
        if (order == null) {
            throw new CustomException("-3", "Qr Code not found", "/" + VIEW_PREFIX + "index");
        }

        // Check if all items are 0 in quantity
        List<Entry<String, Integer>> nonZeroQtyItems = details.getQuantities().entrySet().stream()
                .filter(e -> e.getValue() != 0)
                .toList();
        if (nonZeroQtyItems.isEmpty()) {
            throw new CustomException("-4", "No item selected", "/" + VIEW_PREFIX + "takeOrder/" + qrCode);
        }

        // Loop all the entry in "itemsQty" and save to DB
        for (Entry<String, Integer> entry : nonZeroQtyItems) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrders(order);
            orderDetails.setItems(itemsRepo.findByItemCode(entry.getKey()));
            orderDetails.setQty(entry.getValue());
            detailsRepo.save(orderDetails);
        }
        return "redirect:/" + VIEW_PREFIX + "index";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(ModelMap m, HttpSession session) throws CustomException {
        String qrCode = (String) session.getAttribute("qrCode");
        if (qrCode == null) {
            throw new CustomException("-5", "Qr Code not found", "/" + VIEW_PREFIX + "index");
        }
        m.addAttribute("allOrders", repo.findByQrCode(qrCode));
        return VIEW_PREFIX + "history";
    }
}