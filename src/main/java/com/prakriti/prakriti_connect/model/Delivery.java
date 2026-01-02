package com.prakriti.prakriti_connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "order_id", nullable = false)  // 🔥 THIS FIXES EVERYTHING
    int orderId;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String address;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    int pincode;
}

