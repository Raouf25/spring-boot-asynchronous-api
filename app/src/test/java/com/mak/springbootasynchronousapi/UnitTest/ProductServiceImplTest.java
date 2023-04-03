package com.mak.springbootasynchronousapi.UnitTest;

import com.mak.springbootasynchronousapi.exception.EntityMappingException;
import com.mak.springbootasynchronousapi.model.Product;
import com.mak.springbootasynchronousapi.repository.ProductRepository;
import com.mak.springbootasynchronousapi.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnFluxOfProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Flux.just(new Product(), new Product()));

        // Act
        Flux<Product> result = productService.getAllProducts();

        // Assert
        verify(productRepository).findAll();
        result.as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void getProductById_withValidId_shouldReturnMonoOfProduct() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById( productId)).thenReturn(Mono.just(new Product()));

        // Act
        Mono<Product> result = productService.getProductById(productId);

        // Assert
        verify(productRepository).findById( productId);
        result.as(StepVerifier::create)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }


   @Test
   void getProductById_withValidId_shouldReturnProductMono() {
       // Arrange
       Long productId = 1L;
       Product product = new Product();
       product.setId(productId);
       when(productRepository.findById(productId)).thenReturn(Mono.just(product));

       // Act
       Mono<Product> result = productService.getProductById(productId);

       // Assert
       StepVerifier.create(result)
               .expectNext(product)
               .verifyComplete();
       verify(productRepository, times(1)).findById(productId);
   }

    @Test
    void getProductById_withInvalidId_shouldReturnErrorMono() {
        // Arrange
        Long productId = 1L;
        String errorMessage = String.format("There is no product with id: '%d'", productId);
        when(productRepository.findById(productId)).thenReturn(Mono.empty());

        // Act
        Mono<Product> result = productService.getProductById(productId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof EntityMappingException
                        && throwable.getMessage().equals(errorMessage))
                .verify();
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
     void updateProduct_withValidInputs_shouldReturnUpdatedProduct() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = new Product(productId, "Product 1", "P1", "Description", 10.0, 5, "IN_STOCK", "Category 1", "image1.jpg", 4.5);
        Product updatedProduct = new Product(productId, "Updated Product 1", "UP1", "Updated Description", 12.5, 3, "OUT_OF_STOCK", "Category 2", "image2.jpg", 4.2);

        when(productRepository.findById(productId)).thenReturn(Mono.just(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(Mono.just(updatedProduct));

        // Act
        Mono<Product> result = productService.updateProduct(productId, updatedProduct);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(p -> p.getName().equals(updatedProduct.getName())
                        && p.getCode().equals(updatedProduct.getCode())
                        && p.getDescription().equals(updatedProduct.getDescription())
                        && p.getPrice() == updatedProduct.getPrice()
                        && p.getQuantity()==updatedProduct.getQuantity()
                        && p.getInventoryStatus().equals(updatedProduct.getInventoryStatus())
                        && p.getCategory().equals(updatedProduct.getCategory())
                        && p.getImage().equals(updatedProduct.getImage())
                        && p.getRating() ==updatedProduct.getRating())
                .verifyComplete();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
     void updateProduct_withInvalidProductId_shouldThrowEntityMappingException() {
        // Arrange
        Long productId = 1L;
        Product updatedProduct = new Product(productId, "Updated Product 1", "UP1", "Updated Description", 12.5, 3, "OUT_OF_STOCK", "Category 2", "image2.jpg", 4.2);

        when(productRepository.findById(productId)).thenReturn(Mono.empty());

        // Act
        Mono<Product> result = productService.updateProduct(productId, updatedProduct);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof EntityMappingException
                        && throwable.getMessage().equals("Unable to update product with id1input:" + updatedProduct))
                .verify();

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(0)).save(any(Product.class));
    }


}
