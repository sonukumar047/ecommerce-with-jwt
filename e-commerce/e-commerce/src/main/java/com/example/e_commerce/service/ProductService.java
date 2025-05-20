package com.example.e_commerce.service;

import com.example.e_commerce.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product updateProduct(Integer productId,Product product);
    void deleteProduct(Integer productId);
}
