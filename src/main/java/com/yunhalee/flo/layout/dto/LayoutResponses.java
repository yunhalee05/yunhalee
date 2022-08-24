package com.yunhalee.flo.layout.dto;

import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LayoutResponses {

    private List<LayoutResponse> layouts;

    private LayoutResponses(List<LayoutResponse> layouts) {
        this.layouts = layouts;
    }

    public static LayoutResponses of(List<LayoutResponse> layouts) {
        return new LayoutResponses(layouts);
    }
}
