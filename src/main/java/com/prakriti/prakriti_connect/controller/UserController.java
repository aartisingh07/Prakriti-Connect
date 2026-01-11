package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.AdminDto;
import com.prakriti.prakriti_connect.dto.DisplayDto;
import com.prakriti.prakriti_connect.dto.LoginDto;
import com.prakriti.prakriti_connect.dto.UpdateDto;
import com.prakriti.prakriti_connect.model.Notification;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.NotificationRepo;
import com.prakriti.prakriti_connect.repositories.OrderRepo;
import com.prakriti.prakriti_connect.repositories.ProductRepo;
import com.prakriti.prakriti_connect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    NotificationRepo notificationRepo;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
        return "ALREADY REGISTERED";
    }
        userRepo.save(user);
        return "Signup Successful";
}
    @PostMapping("/login")
    public String login(@RequestBody LoginDto u) {

        User user = userRepo.findByUsername(u.getUsername());

        if (user == null) {
            return "User Not Found";
        }

        if (!u.getPassword().equals(user.getPassword())) {
            return "Password Incorrect";
        }

        if (!u.getRole().equalsIgnoreCase(user.getRole())) {
            return "Invalid Role";
        }
        // ✅ return id and role
        return user.getId() + "," + user.getRole();
    }

    @GetMapping("/get-details/{id}")
    public DisplayDto display(@PathVariable int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));

        DisplayDto displayDto = new DisplayDto();
        displayDto.setId(user.getId());          // 👈 IMPORTANT
        displayDto.setUsername(user.getUsername());
        displayDto.setName(user.getName());

        return displayDto;
    }

    @PostMapping("/update")
    public String update(@RequestBody UpdateDto obj){
        User user = userRepo.findById(obj.getId()).orElseThrow(()->new RuntimeException("Not Found"));

        if(obj.getKey().equalsIgnoreCase("name")){
            if(user.getName().equalsIgnoreCase(obj.getValue())) return "Cannot be same";
            user.setName(obj.getValue());
        }
        else if(obj.getKey().equalsIgnoreCase("password")){
            user.setPassword(obj.getValue());
        }
        else if(obj.getKey().equalsIgnoreCase("email")){
            User user2 = userRepo.findByEmail(obj.getValue());
            if(user2 != null) return "Email already exists";

            user.setEmail(obj.getValue());
        }
        else{
            return "Field update not supported";
        }
        userRepo.save(user);
        return "Profile Update Done Successfully";
    }

    @GetMapping("/admin/get-details/{adminId}")
    public DisplayDto getAdminDetails(@PathVariable int adminId) {

        User admin = userRepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        DisplayDto dto = new DisplayDto();

        dto.setId(admin.getId());
        dto.setName(admin.getName());
        dto.setUsername(admin.getUsername());

        return dto;
    }

    @GetMapping("/admin/dashboard")
    public AdminDto getDashboardStats() {

        AdminDto dto = new AdminDto();

        dto.setTotalUsers(userRepo.count());
        dto.setTotalOrders(orderRepo.count());
        dto.setTotalProducts(productRepo.count());
        dto.setTotalEarnings(orderRepo.getTotalEarnings());

        return dto;
    }

    @GetMapping("/users/{userId}/notifications")
    public List<Notification> getUserNotifications(@PathVariable int userId) {
        return notificationRepo.findUserNotifications(userId);
    }

    @GetMapping("/users/{userId}/notifications/unread-count")
    public long getUnreadNotificationCount(@PathVariable int userId) {
        return notificationRepo.countUnread(userId);
    }

    @PutMapping("/users/{userId}/notifications/read")
    public void markNotificationsAsRead(@PathVariable int userId) {

        List<Notification> notifications = notificationRepo.findUserNotifications(userId);

        for (Notification n : notifications) {
            if (!n.isRead()) {
                n.setRead(true);
            }
        }
        notificationRepo.saveAll(notifications);
    }
}