package com.prakriti.prakriti_connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String productName;
    @Column(name = "image_name", nullable = false)
    private String imageName;
    @Column(nullable = false)
    double price;
    @Column(nullable = false)
    boolean available;
}
