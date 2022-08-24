package com.yunhalee.flo.layout.dto;

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
}
