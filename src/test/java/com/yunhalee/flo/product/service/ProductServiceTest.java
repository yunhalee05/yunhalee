package com.yunhalee.flo.product.service;


import com.yunhalee.flo.ServiceTest;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProductServiceTest extends ServiceTest {

    private static final String ID = "testId";
    private static final String NAME = "testProduct";
    private static final int PRICE = 30;

    @InjectMocks
    private ProductService productService = new ProductService(productRepository);

    private Product product;

    @BeforeEach
    public void setUp() {
        product = Product.builder()
            .id(ID)
            .name(NAME)
            .price(PRICE)
            .build();
    }

    @Test
    public void create_product() {
        // given
        ProductRequest request = new ProductRequest(NAME, PRICE);

        // when
        when(productRepository.save(any())).thenReturn(product);
        ProductResponse response = productService.createProduct(request);

        // then
        check_equals(product, response);
    }

    @Test
    public void find_product() {

        // when
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        ProductResponse response = productService.findProductById(ID);

        // then
        check_equals(product, response);
    }

    @Test
    public void find_products() {
        // given
        Product secondProduct = Product.builder()
            .id(ID)
            .name(NAME)
            .price(PRICE)
            .build();

        // when
        when(productRepository.findAll()).thenReturn(List.of(product, secondProduct));
        ProductResponses response = productService.findProducts();

        // then
        assertThat(response.getProducts().size()).isEqualTo(2);
    }


    private void check_equals(Product product, ProductResponse response) {
        assertThat(response.getId()).isEqualTo(product.getId());
        assertThat(response.getName()).isEqualTo(product.getName());
        assertThat(response.getPrice()).isEqualTo(product.getPrice());
    }

}