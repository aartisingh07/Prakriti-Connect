package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(int userId);
}
