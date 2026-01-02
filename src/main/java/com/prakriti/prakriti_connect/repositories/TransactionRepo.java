package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
}
