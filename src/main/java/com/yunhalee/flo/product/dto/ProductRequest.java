package com.yunhalee.flo.product.dto;

import com.yunhalee.flo.product.domain.Product;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductRequest {

    public String name;
    public int price;

    public ProductRequest(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return Product.builder()
            .name(name)
            .price(price)
            .build();
    }

}
