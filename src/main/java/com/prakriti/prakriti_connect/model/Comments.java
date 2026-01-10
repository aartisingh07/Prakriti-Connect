package com.prakriti.prakriti_connect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    int newsId;

    @Column(nullable = false)
    int userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    String commentText;

    @CreationTimestamp
    LocalDateTime commentDate;
}