package com.yunhalee.flo.product.service;

import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import com.yunhalee.flo.product.exception.ProductNotFoundException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private static final String PRODUCT_NOT_FOUND_EXCEPTION = "Cannot find product with id : ";
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productRepository.save(request.toProduct());
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(String id) {
        Product product = findById(id);
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    public ProductResponses findProducts() {
        return ProductResponses.of(productRepository.findAll().stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList()));
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = findById(id);
        product.update(request);
        return ProductResponse.of(product);
    }

    public void deleteProduct(String id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public Product findById(String id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_EXCEPTION + id));
    }


}
