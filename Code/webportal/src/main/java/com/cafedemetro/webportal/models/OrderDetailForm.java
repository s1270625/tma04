package com.cafedemetro.webportal.models;

import java.util.HashMap;
import lombok.Data;

@Data
public class OrderDetailForm {
    private HashMap<String, Integer> quantities;
}