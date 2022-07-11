package com.product.test.controller;

import com.product.test.model.Stock;
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
public class StockController {
	 @Autowired
	 StockRepository stockRepository;

	    @GetMapping(path = "/stocks")
	    public Flux<Stock> handleGetStocks()
	    {
	        return stockRepository.findAll();
	    }
	    @GetMapping(path = "/stocks/{stockId}")
	    public Mono<ResponseEntity<Stock>> handleGetStocks(@PathVariable String stockId)
	    {
	        Mono<ResponseEntity<Stock>> responseEntityMono;
	        responseEntityMono = stockRepository.findById(stockId)
	                .map(x -> new ResponseEntity<Stock>(x, HttpStatus.OK))
	                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	        return responseEntityMono;
	    }

	    @PostMapping(path = "/stocks")
	    public Mono<Stock> handlePostStock(@RequestBody Stock stock)
	    {
	        return stockRepository.save(stock);
	    }

	    @DeleteMapping(path = "/stocks/{stockId}")
	    public Mono<Void> handleDeleteStock(@PathVariable String stockId)
	    {
	        return stockRepository.deleteById(stockId);
	    }

	    @PutMapping(path = "/stocks/{stockId}")
	    public Mono<ResponseEntity<Stock>> handlePutStock(@PathVariable String stockId,@RequestBody Stock stock)
	    {
	        return stockRepository.findById(stockId)
	                        .flatMap(x -> {
	                            x.setId(stock.getId());
	                            Mono<Stock> updatedStocks =stockRepository.save(x);
	                            return updatedStocks;
	                        })
	                        .repeatWhenEmpty(Repeat.times(5))
	                        .map(x -> new ResponseEntity<>(x, HttpStatus.OK))
	                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    private boolean validateStock(Stock x)
	    {
	        return x.getId() != null;
	    }
}
