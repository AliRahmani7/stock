package com.product.test;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.product.test.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ProductControllerTest {
	
    @MockBean
    private ProductRepository productRepository;
    
    @Autowired
    private WebTestClient testClient;
    
    @Autowired
    private ProductConfig config;
    
    @Test
    public void testFindAll() 
    {
        // given
    	Product product1=new Product("101", "Laptop", "Ultra-light weight body with Ultra portability Laptop", "Electronics");
    	Product product2=new Product("102", "Shoe", "Comfortable fabric lining and lightly-padded tongue for added support", "Clothing");
    	Product product3=new Product("103", "iPhon", "The silky, soft-touch finish of the silicone exterior feels great in your hand", "Electronics");
    	Product product4=new Product("104", "Pillow", "The poly fiber filling and the top quality materia", "Bedding");
    	Product product5=new Product("105", "Fridge", "Energy saving and environmentally friendly", "Home Appliances");
    	Product product6=new Product("106", "Desk", "Table with 2 open shelves ideal for study, bedroom, living room", "Furnitue");
	
    	List<Product> list = Arrays.asList(product1,product2,product3,product4,product5,product6);
    	Flux<Product> flux=Flux.fromIterable(list);
    	flux.subscribe(System.out::println);
    }
    @Test
    void givenProductId_whenGetProductById_thenCorrectProduct() {

    	Product product = new Product("101", "Laptop", "Ultra-light weight body with Ultra portability Laptop", "Electronics");

        given(productRepository.findProductById("101")).willReturn(Mono.just(product));

        testClient.get()
          .uri("http://localhost:8080/products/101")
          .exchange()
          .expectStatus().isOk()
          .expectBody(Product.class).isEqualTo(product);
    }
    @Test
    void whenGetAllEmployees_thenCorrectEmployees() {
        WebTestClient client = WebTestClient.bindToRouterFunction(config.getAllProductsRoute())
            .build();

        List<Product> products = Arrays.asList(new Product("101", "Laptop", "Ultra-light weight body with Ultra portability Laptop", "Electronics"),
        		new Product("102", "Shoe", "Comfortable fabric lining and lightly-padded tongue for added support", "Clothing"));

        Flux<Product> productFlux = Flux.fromIterable(products);
        given(productRepository.findAllProducts()).willReturn(productFlux);

        client.get()
            .uri("http://localhost:8080/ptoducts")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Product.class)
            .isEqualTo(products);
    }
}
