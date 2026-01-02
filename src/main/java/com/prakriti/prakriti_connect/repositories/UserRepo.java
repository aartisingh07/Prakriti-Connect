package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {


    User findByUsername(String username);

    User findByEmail(String value);

    boolean existsByUsername(String username);
}
