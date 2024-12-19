package com.example.checkout_component.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Receipt {
    private Map<String, Integer> items;
    private double totalPrice;
    private String details;
}
