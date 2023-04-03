package com.mak.springbootasynchronousapi.IntegrationTest;

import com.mak.springbootasynchronousapi.controller.ProductRestController;
import com.mak.springbootasynchronousapi.model.Product;
import com.mak.springbootasynchronousapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@Slf4j
class ProductRestControllerIntegrationTest {


    private WebTestClient webTestClient;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductRestController productRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(productRestController).build();
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(100.0);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(200.0);
        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(Flux.fromIterable(productList));

        webTestClient.get().uri("/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .isEqualTo(productList);
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setPrice(100.0);

        when(productService.getProductById(productId)).thenReturn(Mono.just(product));

        webTestClient.get().uri("/products/{id}", productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(product);
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Product 1");
        savedProduct.setPrice(100.0);
        when(productService.addProduct(product)).thenReturn(Mono.just(savedProduct));

        webTestClient.post().uri("/products")
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(savedProduct);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setPrice(100.0);
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Product 1 Updated");
        updatedProduct.setPrice(150.0);

        when(productService.updateProduct(productId, product)).thenReturn(Mono.just(updatedProduct));

        webTestClient.patch().uri("/products/{id}", productId)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(updatedProduct);
    }

    @Test
    void testDeleteProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Product 1");
        product.setPrice(100.0);

        when(productService.deleteProductById(productId)).thenReturn(Mono.just(product));

        webTestClient.delete().uri("/products/{id}", productId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
