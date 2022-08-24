package com.yunhalee.flo.layout.dto;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.product.dto.ProductResponses;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LayoutResponse {

    private String id;
    private String name;
    private ProductResponses products;

    public LayoutResponse(Layout layout, ProductResponses products) {
        this.id = layout.getId();
        this.name = layout.getName();
        this.products = products;
    }

    public static LayoutResponse of(Layout layout, ProductResponses products) {
        return new LayoutResponse(layout, products);
    }

}
