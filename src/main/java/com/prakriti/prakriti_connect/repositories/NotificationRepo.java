package com.prakriti.prakriti_connect.repositories;

import com.prakriti.prakriti_connect.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    @Query("""
        SELECT n FROM Notification n
        WHERE n.userId = :userId OR n.userId = 0
        ORDER BY n.createdAt DESC
    """)
    List<Notification> findUserNotifications(@Param("userId") int userId);

    @Query("""
        SELECT COUNT(n) FROM Notification n
        WHERE (n.userId = :userId OR n.userId = 0)
        AND n.isRead = false
    """)
    long countUnread(@Param("userId") int userId);
    @Query("""
    DELETE FROM Notification n
    WHERE n.userId = :userId
""")
    @Modifying
    void deleteAllForUser(@Param("userId") int userId);
}
