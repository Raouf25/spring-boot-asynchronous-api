package com.mak.springbootasynchronousapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Product {
    @Id
    private Long id;

    private String code;

    private String name;

    private String description;

    private double price;

    private int quantity;

    private String inventoryStatus;

    private String category;

    private String image;

    private double rating;
}
