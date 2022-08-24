package com.yunhalee.flo.product.service;

import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.domain.ProductRepository;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import com.yunhalee.flo.product.exception.ProductNameAlreadyExistsException;
import com.yunhalee.flo.product.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        checkNameDuplicated(request.getName());
        Product product = productRepository.save(request.toProduct());
        return ProductResponse.of(product);
    }

    private void checkNameDuplicated(String name) {
        if (productRepository.existsByName(name)) {
            throw new ProductNameAlreadyExistsException("Product already exists with name : " + name);
        }
    }

    public ProductResponse findProductById(String id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Cannot find product with id : " + id));
        return ProductResponse.of(product);
    }

    public ProductResponses findProducts() {
        List<Product> products = productRepository.findAll();
        return ProductResponses.of(products.stream()
            .map(product -> ProductResponse.of(product))
            .collect(Collectors.toList()));
    }
}
