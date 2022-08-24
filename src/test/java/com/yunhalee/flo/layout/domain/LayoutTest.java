package com.yunhalee.flo.layout.domain;

import static com.yunhalee.flo.product.domain.ProductTest.FIRST_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.SECOND_PRODUCT;
import static com.yunhalee.flo.product.domain.ProductTest.THIRD_PRODUCT;

import java.util.Arrays;

public class LayoutTest {

    public static final Layout FIRST_LAYOUT = Layout.builder()
        .id("1")
        .name("firstLayout")
        .products(Arrays.asList(FIRST_PRODUCT, SECOND_PRODUCT))
        .build();

    public static final Layout SECOND_LAYOUT = Layout.builder()
        .id("2")
        .name("secondLayout")
        .products(Arrays.asList(THIRD_PRODUCT))
        .build();


}