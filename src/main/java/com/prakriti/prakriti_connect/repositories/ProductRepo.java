package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    long count();
}
