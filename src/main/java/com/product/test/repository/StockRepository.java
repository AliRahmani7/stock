package com.product.test.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.product.test.model.Stock;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String>{
//public interface CustomerRepository extends ReactiveCrudRepository<Customer, String>{
}
