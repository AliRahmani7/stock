package com.product.test;

import com.product.test.model.Stock;
import com.product.test.model.Product;
import com.product.test.repository.ProductRepository;
import com.product.test.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner
{

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;

    public List<Product> productData()
    {
        return Arrays.asList(new Product("101", "Laptop", "Ultra-light weight body with Ultra portability Laptop", "Electronics"),
                new Product("102", "Shoe", "Comfortable fabric lining and lightly-padded tongue for added support", "Clothing"),
                new Product("103", "iPhon", "The silky, soft-touch finish of the silicone exterior feels great in your hand", "Electronics"),
                new Product("104", "Pillow", "The poly fiber filling and the top quality materia", "Bedding"),
                new Product("105", "Fridge", "Energy saving and environmentally friendly", "Home Appliances"),
                new Product("106", "Desk", "Table with 2 open shelves ideal for study, bedroom, living room", "Furnitue"));
    }
    public List<Stock> stockData()
    {
        return Arrays.asList(new Stock("801", "101", 45),
                new Stock("802", "102", 176),
                new Stock("803", "103", 676),
                new Stock("804", "104", 96),
                new Stock("805", "105", 36),
                new Stock("806", "106", 88));
    }
    @Override
    public void run(String... args) throws Exception {
        initialize();
    }

    private void initialize() {

        Mono<Void> deletedObjects = productRepository.deleteAll();

        List<Product> productList = productData();
        productRepository
                .deleteAll()
                .thenMany(Flux.fromIterable(productList))
                .flatMap(x -> {

                    Mono<Product> newlyAddedProduct = productRepository.save(x);
                    return newlyAddedProduct;
                })
                .thenMany(productRepository.findAll())
                .subscribe(x -> {
                    System.out.println(x);
                });
        stockRepository
        .deleteAll()
        .thenMany(Flux.fromIterable(stockData()))
        .flatMap(x -> {

            Mono<Stock> newlyInsertedStock = stockRepository.save(x);
            return newlyInsertedStock;
        }).thenMany(stockRepository.findAll())
        .subscribe(x -> {
            System.out.println(x);
        });
    }
}
