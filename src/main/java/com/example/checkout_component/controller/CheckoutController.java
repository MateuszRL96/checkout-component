package com.example.checkout_component.controller;

import com.example.checkout_component.model.Receipt;
import com.example.checkout_component.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }

    @PostMapping("/scan/{productName}")
    public ResponseEntity<String> scanItem(@PathVariable String productName){
        checkoutService.scanItem(productName);
        return ResponseEntity.ok("Product " + productName + " add to card");
    }

    @GetMapping("/total")
    public ResponseEntity<Receipt> getTotal() {
        return ResponseEntity.ok(checkoutService.calculateTotal());
    }

    @PostMapping("/pay")
    public ResponseEntity<Receipt> pay() {
        Receipt receipt = checkoutService.calculateTotal();
        checkoutService.clearCart();
        return ResponseEntity.ok(receipt);
    }
}
