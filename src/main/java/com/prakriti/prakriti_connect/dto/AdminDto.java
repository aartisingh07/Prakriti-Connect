package com.prakriti.prakriti_connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    long totalUsers;
    long totalOrders;
    long totalProducts;
    double totalEarnings;
}
