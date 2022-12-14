package com.yunhalee.flo.layout.dto;

import com.yunhalee.flo.layout.domain.Layout;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LayoutRequest {

    private String name;
    private List<String> products;

    public LayoutRequest(String name, List<String> products) {
        this.name = name;
        this.products = products;
    }

    public Layout toLayout() {
        return Layout.builder()
            .name(name)
            .products(new ArrayList<>())
            .build();
    }
}
