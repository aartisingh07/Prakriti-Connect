package com.prakriti.prakriti_connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    int userId;
    int productId;
    String name;
    String phone;
    String address;
    String city;
    int pincode;
}
