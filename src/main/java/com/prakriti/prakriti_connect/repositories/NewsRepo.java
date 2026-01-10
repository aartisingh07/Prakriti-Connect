package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<News, Integer> {
}
