package com.prakriti.prakriti_connect.controller;

import com.prakriti.prakriti_connect.dto.AddProductDto;
import com.prakriti.prakriti_connect.model.Product;
import com.prakriti.prakriti_connect.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping("/admin/products")
    public String addProduct(@RequestBody AddProductDto dto) {

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());

        // default values
        product.setImageName("default-product.png");
        product.setAvailable(true);

        productRepo.save(product);
        return "Product Added Successfully";
    }

    @PutMapping("/admin/products/{id}/toggle")
    public Product toggleProduct(@PathVariable int id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setAvailable(!product.isAvailable());
        return productRepo.save(product);
    }

}
