package com.product.test.controller;

import com.product.test.model.Product;
import com.product.test.model.Stock;
import com.product.test.repository.ProductRepository;
import com.product.test.repository.StockRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Repeat;

@RestController
@Slf4j
public class ProductController
{
    @Autowired
    ProductRepository productRepository;
    @Autowired
	StockRepository stockRepository;
    
	@GetMapping("/")					
	String testServer() {
	    return "Server Running !";
	}
	
    @GetMapping(path = "/products")
    public Flux<Product> handleGetProducts()
    {
        return productRepository.findAll();
    }

    @GetMapping(path = "/products/{productId}")
    public Mono<ResponseEntity<Product>> handleGetProduct(@PathVariable String productId)
    {
        Mono<ResponseEntity<Product>> responseEntityMono;
        responseEntityMono = productRepository.findById(productId)
                .map(x -> new ResponseEntity<Product>(x, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return responseEntityMono;
    }

    @PostMapping(path = "/products")
    public Mono<Product> handlePostProduct(@RequestBody Product product)
    {
        return productRepository.save(product);
    }

    @DeleteMapping(path = "/products/{productId}")
    public Mono<Void> handleDeleteProduct(@PathVariable String productId)
    {
        return productRepository.deleteById(productId);
    }

    @PutMapping(path = "/products/{productId}")
    public Mono<ResponseEntity<Product>> handlePutProduct(@PathVariable String productId,@RequestBody Product product)
    {
        return productRepository.findById(productId)
                        .flatMap(x -> {
                            x.setName(product.getName());
                            Mono<Product> updatedProduct =productRepository.save(x);
                            return updatedProduct;
                        })
                        .repeatWhenEmpty(Repeat.times(5))
                        .map(x -> new ResponseEntity<>(x, HttpStatus.OK))
                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
     //////////////////////    TODO   /////////////////////////////
    /*  @GetMapping(path = "/bookproduct/{productId}")
    public Mono<ResponseEntity<Product>> GetProductId(@PathVariable String productId)
    {

    }*/

    private boolean validateProduct(Product x)
    {
        return x.getName() != null;
    }
}
