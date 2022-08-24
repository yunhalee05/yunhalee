package com.yunhalee.flo.layout.dto;

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
