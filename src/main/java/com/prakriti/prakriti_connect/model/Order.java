package com.prakriti.prakriti_connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    int userId;

    @Column(nullable = false)
    int productId;

    @Column(nullable = false)
    String status;

    @CreationTimestamp
    LocalDateTime orderDate;

    LocalDateTime deliveryDate;
}