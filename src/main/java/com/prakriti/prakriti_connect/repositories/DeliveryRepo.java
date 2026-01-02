package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Integer> {
}
