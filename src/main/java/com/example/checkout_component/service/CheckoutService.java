package com.example.checkout_component.service;

import com.example.checkout_component.model.Product;
import com.example.checkout_component.model.Receipt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckoutService {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Integer> cart = new HashMap<>();

    public CheckoutService(){
        products.put("A", new Product("A", 40, 3, 30));
        products.put("B", new Product("B", 10, 2, 7.5));
        products.put("C", new Product("C", 30, 4, 20));
        products.put("D", new Product("D", 25, 2, 23.5));
    }

    public void scanItem(String productName){
        cart.put(productName, cart.getOrDefault(productName, 0) +1);
    }

    public Receipt calculateTotal(){
        double totalPrice = 0;
        StringBuilder details = new StringBuilder("Discount applied: \n");
        for(Map.Entry<String, Integer> entry :cart.entrySet()){
            Product product = products.get(entry.getKey());
            int quantity = entry.getValue();

            if(quantity >= product.getDiscountQuantity()){
                int promoBatches = quantity / product.getDiscountQuantity();
                int remainingItems = quantity % product.getDiscountQuantity();
                totalPrice += promoBatches * product.getDiscountQuantity() * product.getDiscountPrice();
                totalPrice += remainingItems * product.getPrice();

                details.append(String.format("  %s: %d x %.2f (promo applied)\n",
                        product.getName(), quantity, product.getDiscountPrice()));
            } else {
                totalPrice += quantity * product.getPrice();
            }
        }
        return new Receipt(cart, totalPrice, details.toString());
    }

    public void clearCart(){
        cart.clear();
    }
}
