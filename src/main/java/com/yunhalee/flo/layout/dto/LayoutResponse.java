package com.yunhalee.flo.layout.dto;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.product.dto.ProductResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LayoutResponse {

    private String id;
    private String name;
    private List<ProductResponse> products;

    public LayoutResponse(Layout layout, List<ProductResponse> products) {
        this.id = layout.getId();
        this.name = layout.getName();
        this.products = products;
    }

    public static LayoutResponse of(Layout layout, List<ProductResponse> products) {
        return new LayoutResponse(layout, products);
    }

}
