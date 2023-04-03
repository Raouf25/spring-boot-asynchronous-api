package com.mak.springbootasynchronousapi.controller;

import com.mak.springbootasynchronousapi.model.Product;
import com.mak.springbootasynchronousapi.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Slf4j
@RestController
@RequestMapping("/products")
@Api(tags = "ProductRestController", value = "Product Rest API")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    @ApiOperation(value = "Get all products", notes = "Retrieve all products from the database")
    Flux<Product> getAllProducts() {
        log.info("Retrieving all products");
        return processWithLog(this.productService.getAllProducts());
    }

    @GetMapping("all")
    public Mono<Page<Product>> getAll(@RequestParam("page") int page, @RequestParam("size") int size){
        return this.productService.getProducts(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Get product by id", notes = "Retrieve a product from the database by id")
    Mono<Product> getProductById(@PathVariable Long id) {
        log.info("Retrieving product with id: '{}'", id);
        return processWithLog(this.productService.getProductById(id));
    }

    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a product", notes = "Add a new product to the database")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Product> addProduct(@RequestBody Product product) {
        log.info("Adding product to repository");
        return processWithLog(this.productService.addProduct(product));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update a product", notes = "Update an existing product in the database")
    Mono<Product> updateProduct(@Validated @PathVariable Long id, @RequestBody Product product) {
        log.info("Updating product with id: {}", id);
        return processWithLog(this.productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product", notes = "Delete a product from the database by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> deleteProductById(@PathVariable Long id) {
        log.info("Removing product with id: {}", id);
        return processWithLog(this.productService.deleteProductById(id))
                .then(); // Utilisation de then() pour transformer en Mono<Void>
    }

    private <T> Mono<T> processWithLog(Mono<T> monoToLog) {
        return monoToLog
                .log("ProductRestController.", Level.INFO, SignalType.ON_NEXT, SignalType.ON_COMPLETE);
    }

    private <T> Flux<T> processWithLog(Flux<T> fluxToLog) {
        return fluxToLog
                .log("ProductRestController.", Level.INFO, SignalType.ON_NEXT, SignalType.ON_COMPLETE);
    }
}
