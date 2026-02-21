package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.AdminOrderDto;
import com.prakriti.prakriti_connect.dto.CustomerOrderDto;
import com.prakriti.prakriti_connect.dto.OrderDto;
import com.prakriti.prakriti_connect.model.Notification;
import com.prakriti.prakriti_connect.model.Order;
import com.prakriti.prakriti_connect.model.Product;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.NotificationRepo;
import com.prakriti.prakriti_connect.repositories.OrderRepo;
import com.prakriti.prakriti_connect.repositories.ProductRepo;
import com.prakriti.prakriti_connect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    NotificationRepo notificationRepo;

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody OrderDto orderDto) {

        // 🔹 Fetch product
        Product product = productRepo.findById(orderDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 🔹 Create Order
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setProductId(orderDto.getProductId());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDate(LocalDateTime.now().plusDays(5));
        order.setTotalAmount(product.getPrice() + 10);

        Order savedOrder = orderRepo.save(order);

        // 🔔 CREATE NOTIFICATION FOR USER
        Notification notification = new Notification();
        notification.setUserId(orderDto.getUserId());
        notification.setMessage("📦 Your order will be delivered in the next 5 days.");
        notification.setType("ORDER");
        notification.setRead(false);

        notificationRepo.save(notification);

        return savedOrder;
    }

    @GetMapping("/orders/user/{userId}")
    public List<CustomerOrderDto> getOrdersByUserId(@PathVariable int userId) {

        List<Order> orders = orderRepo.findByUserId(userId);
        List<CustomerOrderDto> list = new ArrayList<>();

        for (Order o : orders) {

            Product product = productRepo.findById(o.getProductId()).orElse(null);

            CustomerOrderDto dto = new CustomerOrderDto();
            dto.setOrderId(o.getId());
            dto.setProductName(product != null ? product.getProductName() : "NA");
            dto.setStatus(o.getStatus());
            dto.setTotalAmount(o.getTotalAmount());
            dto.setOrderDate(o.getOrderDate());

            list.add(dto);
        }

        return list;
    }

    @GetMapping("/admin/orders")
    public List<AdminOrderDto> getAllOrders() {

        List<Order> orders = orderRepo.findAll();
        List<AdminOrderDto> list = new ArrayList<>();

        for (Order o : orders) {

            User user = userRepo.findById(o.getUserId()).orElse(null);
            Product product = productRepo.findById(o.getProductId()).orElse(null);

            AdminOrderDto dto = new AdminOrderDto();
            dto.setOrderId(o.getId());
            dto.setUsername(user != null ? user.getUsername() : "NA");
            dto.setProductName(product != null ? product.getProductName() : "NA");
            dto.setTotalAmount(o.getTotalAmount());
            dto.setStatus(o.getStatus());
            dto.setOrderDate(o.getOrderDate());

            list.add(dto);
        }
        return list;
    }
}