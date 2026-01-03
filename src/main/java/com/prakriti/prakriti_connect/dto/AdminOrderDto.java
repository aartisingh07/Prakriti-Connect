package com.prakriti.prakriti_connect.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminOrderDto {
    int orderId;
    String username;
    String productName;
    double totalAmount;
    String status;
    LocalDateTime orderDate;
}
