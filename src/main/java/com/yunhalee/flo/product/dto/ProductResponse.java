package com.yunhalee.flo.product.dto;

import com.yunhalee.flo.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private String id;
    private String name;
    private int price;

    private ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(product);
    }

}
