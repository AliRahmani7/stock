package com.product.test.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.product.test.model.Product;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
//public interface CustomerRepository extends ReactiveCrudRepository<Customer, String>{
}
