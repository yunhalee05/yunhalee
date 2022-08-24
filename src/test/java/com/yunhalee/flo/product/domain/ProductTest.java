package com.yunhalee.flo.product.domain;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    public static final Product FIRST_PRODUCT = Product.builder()
            .id("1")
            .name("testFirstProduct")
            .price(10)
            .build();

    public static final Product SECOND_PRODUCT = Product.builder()
        .id("1")
        .name("testSecondProduct")
        .price(20)
        .build();

    public static final Product THIRD_PRODUCT = Product.builder()
        .id("3")
        .name("testThirdProduct")
        .price(30)
        .build();
}