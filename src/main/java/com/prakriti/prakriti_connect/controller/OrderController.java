package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.AdminDto;
import com.prakriti.prakriti_connect.dto.AdminOrderDto;
import com.prakriti.prakriti_connect.dto.OrderDto;
import com.prakriti.prakriti_connect.model.Delivery;
import com.prakriti.prakriti_connect.model.Order;
import com.prakriti.prakriti_connect.model.Product;
import com.prakriti.prakriti_connect.model.User;
import com.prakriti.prakriti_connect.repositories.DeliveryRepo;
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
    DeliveryRepo deliveryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;


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

        // 🔹 Create Delivery
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
