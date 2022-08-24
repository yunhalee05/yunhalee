package com.yunhalee.flo.product.dto;

import com.yunhalee.flo.product.domain.Product;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductResponses {

    private List<ProductResponse> products;

    private ProductResponses(List<ProductResponse> products) {
        this.products = products;
    }

    public static ProductResponses of(List<ProductResponse> products) {
        return new ProductResponses(products);
    }
}
