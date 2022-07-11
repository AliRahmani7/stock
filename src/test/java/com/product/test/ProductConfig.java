package com.product.test;

import com.product.test.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class ProductConfig {

    @Bean
    ProductRepository ProductRepository() {
        return new ProductRepository();
    }

    @Bean
    RouterFunction<ServerResponse> getAllProductsRoute() {
        return route(GET("http://localhost:8080/products"), req -> ok().body(ProductRepository().findAllProducts(), Product.class));
    }

    @Bean
    RouterFunction<ServerResponse> getProductByIdRoute() {
        return route(GET("http://localhost:8080/products/{id}"), req -> ok().body(ProductRepository().findProductById(req.pathVariable("id")), Product.class));
    }

    @Bean
    RouterFunction<ServerResponse> updateProductRoute() {
        return route(POST("http://localhost:8080/roducts/update"), req -> req.body(toMono(Product.class))
            .doOnNext(ProductRepository()::updateProduct)
            .then(ok().build()));
    }

    @Bean
    RouterFunction<ServerResponse> composedRoutes() {
        return route(GET("http://localhost:8080/products"), req -> ok().body(ProductRepository().findAllProducts(), Product.class))

            .and(route(GET("http://localhost:8080/products/{id}"), req -> ok().body(ProductRepository().findProductById(req.pathVariable("id")), Product.class)))

            .and(route(POST("http://localhost:8080/products/update"), req -> req.body(toMono(Product.class))
                .doOnNext(ProductRepository()::updateProduct)
                .then(ok().build())));
    }
}
