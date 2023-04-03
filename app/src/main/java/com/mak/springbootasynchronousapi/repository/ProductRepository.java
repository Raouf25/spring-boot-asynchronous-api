package com.mak.springbootasynchronousapi.repository;

import com.mak.springbootasynchronousapi.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveSortingRepository<Product, Long> , ReactiveCrudRepository<Product, Long> {

    Flux<Product> findAllBy(Pageable pageable);
}
