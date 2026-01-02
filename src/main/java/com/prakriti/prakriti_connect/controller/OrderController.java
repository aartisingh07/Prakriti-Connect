package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.OrderDto;
import com.prakriti.prakriti_connect.model.Delivery;
import com.prakriti.prakriti_connect.model.Order;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.DeliveryRepo;
import com.prakriti.prakriti_connect.repositories.OrderRepo;
import com.prakriti.prakriti_connect.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    DeliveryRepo deliveryRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/orders")
    public Order placeOrder(@RequestBody OrderDto orderDto) {

        // 1️⃣ Create and save Order
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setProductId(orderDto.getProductId());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDate(LocalDateTime.now().plusDays(5));

        Order savedOrder = orderRepo.save(order);

        // 2️⃣ Create and save Delivery
        Delivery delivery = new Delivery();
        delivery.setOrderId(savedOrder.getId());
        delivery.setName(orderDto.getName());
        delivery.setPhone(orderDto.getPhone());
        delivery.setAddress(orderDto.getAddress());
        delivery.setCity(orderDto.getCity());
        delivery.setPincode(orderDto.getPincode());

        deliveryRepo.save(delivery);
        return savedOrder;
    }

    @GetMapping("/orders/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable int userId) {
        return orderRepo.findByUserId(userId);
    }
}
