package com.mak.springbootasynchronousapi.service;

import com.mak.springbootasynchronousapi.model.Product;
import com.mak.springbootasynchronousapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getAllProducts() {
        final String errorMessage = "There is an issue getting all of products.";
        return Utils.processWithErrorCheck(this.productRepository.findAll(), errorMessage);
    }

    public Mono<Page<Product>> getProducts(PageRequest pageRequest){
        return this.productRepository.findAllBy(pageRequest.withSort(Sort.by("price").descending()))
                .collectList()
                .zipWith(this.productRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }


    public Mono<Product> getProductById(Long id) {
        final String errorMessage = String.format("There is no product with id: '%d'", id);
        return Utils.processWithErrorCheck(this.productRepository.findById(id), errorMessage);
    }

    public Mono<Product> addProduct(Product product) {
        final String errorMessage = "Unable to add product with input:" + product;
        return Utils.processWithErrorCheck(this.productRepository.save(product), errorMessage);
    }

    public Mono<Product> updateProduct(Long id, Product productInput) {
        final String errorMessage =
                "Unable to update product with id" + id + "input:" + productInput;
        return Utils.processWithErrorCheck(this.productRepository.findById(Objects.requireNonNull(id)),errorMessage)
                .flatMap(product -> this.productRepository.save(product).log());
    }

    @Override
    public Mono<Product> deleteProductById(Long id) {
        return getProductById(id).map(product -> {
            this.productRepository.deleteById(id).subscribe();
            return product;
        });
    }
}
