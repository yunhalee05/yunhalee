package com.yunhalee.flo.layout.service;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.layout.domain.LayoutRepository;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.exception.LayoutNotFoundException;
import com.yunhalee.flo.product.domain.Product;
import com.yunhalee.flo.product.dto.ProductResponse;
import com.yunhalee.flo.product.dto.ProductResponses;
import com.yunhalee.flo.product.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LayoutService {

    private LayoutRepository layoutRepository;
    private ProductService productService;

    public LayoutService(LayoutRepository layoutRepository, ProductService productService) {
        this.layoutRepository = layoutRepository;
        this.productService = productService;
    }

    public LayoutResponse createLayout(LayoutRequest request) {
        List<Product> products = request.getProducts().stream()
            .map(id -> productService.findById(id))
            .collect(Collectors.toList());
        Layout layout = layoutRepository.save(request.toLayout());
        layout.addProducts(products);
        return LayoutResponse.of(layout,
            ProductResponses.of(products.stream()
                    .map(product -> ProductResponse.of(product))
                    .collect(Collectors.toList())
            ));
    }

    public LayoutResponse updateLayout(String id, LayoutRequest request) {
        Layout layout = findById(id);
        List<Product> products = request.getProducts().stream()
            .map(productId -> productService.findById(productId))
            .collect(Collectors.toList());
        layout.update(request.getName(), products);
        return LayoutResponse.of(layout,
            ProductResponses.of(products.stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toList())
            ));
    }

    private Layout findById(String id) {
        return layoutRepository.findById(id)
            .orElseThrow(() -> new LayoutNotFoundException("Layout does not exist with id : " + id));
    }
}
