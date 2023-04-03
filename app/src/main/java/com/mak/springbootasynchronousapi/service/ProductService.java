package com.mak.springbootasynchronousapi.service;

import com.mak.springbootasynchronousapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> getProductById(Long id);

    Flux<Product> getAllProducts();

    Mono<Page<Product>> getProducts(PageRequest pageRequest);

    Mono<Product> addProduct(Product playerInput);

    Mono<Product> updateProduct(Long id, Product player);

    Mono<Product> deleteProductById(Long id);
}
