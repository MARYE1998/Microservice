package com.programmingtechie.product.service.repository;

import com.programmingtechie.product.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
