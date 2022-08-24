package com.yunhalee.flo.product.domain;

import com.yunhalee.flo.RepositoryTest;
import org.junit.Before;

class ProductRepositoryTest extends RepositoryTest {

    private Product fistProduct;
    private Product secondProduct;
    private Product thirdProduct;

    @Before
    public void setUp() {
        fistProduct = save("firstProduct", 10);
        secondProduct = save("secondProduct", 20);
        thirdProduct = save("thirdProduct", 30);
    }

    private Product save(String name, int price) {
        return productRepository.save(Product.builder()
            .name(name)
            .price(price)
            .build());
    }
}