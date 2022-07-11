package com.product.test;

import org.springframework.stereotype.Repository;

import com.product.test.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
	private static final Map<String, Product> PRODUCT_DATA;
	
    static {
        PRODUCT_DATA = new HashMap<>();
        PRODUCT_DATA.put("1", new Product("101", "Laptop", "Ultra-light weight body with Ultra portability Laptop", "Electronics"));
        PRODUCT_DATA.put("2", new Product("102", "Shoe", "Comfortable fabric lining and lightly-padded tongue for added support", "Clothing"));
        PRODUCT_DATA.put("3", new Product("103", "iPhon", "The silky, soft-touch finish of the silicone exterior feels great in your hand", "Electronics"));
        PRODUCT_DATA.put("4", new Product("104", "Pillow", "The poly fiber filling and the top quality materia", "Bedding"));
        PRODUCT_DATA.put("5", new Product("105", "Fridge", "Energy saving and environmentally friendly", "Home Appliances"));
        PRODUCT_DATA.put("6", new Product("106", "Desk", "Table with 2 open shelves ideal for study, bedroom, living room", "Furnitue"));
        
    }

    public Mono<Product> findProductById(String id) {
        return Mono.just(PRODUCT_DATA.get(id));
    }

    public Flux<Product> findAllProducts() {
        return Flux.fromIterable(PRODUCT_DATA.values());
    }

    public Mono<Product> updateProduct(Product Product) {
        Product existingProduct = PRODUCT_DATA.get(Product.getId());
        if (existingProduct != null) {
            existingProduct.setName(Product.getName());
        }
        return Mono.just(existingProduct);
    }
}

