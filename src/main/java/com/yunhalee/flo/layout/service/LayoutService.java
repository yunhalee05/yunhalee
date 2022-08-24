package com.yunhalee.flo.layout.service;

import com.yunhalee.flo.layout.domain.Layout;
import com.yunhalee.flo.layout.domain.LayoutRepository;
import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.dto.LayoutResponses;
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

    private static final String LAYOUT_NOT_FOUND_EXCEPTION = "Layout does not exist with id : ";

    private LayoutRepository layoutRepository;
    private ProductService productService;

    public LayoutService(LayoutRepository layoutRepository, ProductService productService) {
        this.layoutRepository = layoutRepository;
        this.productService = productService;
    }

    public LayoutResponse createLayout(LayoutRequest request) {
        List<Product> products = products(request.getProducts());
        Layout layout = layoutRepository.save(request.toLayout());
        layout.addProducts(products);
        return layoutResponse(layout);
    }

    @Transactional(readOnly = true)
    public LayoutResponse findLayout(String id) {
        Layout layout = findById(id);
        return layoutResponse(layout);
    }

    @Transactional(readOnly = true)
    public LayoutResponses findLayouts() {
        return LayoutResponses.of(layoutRepository.findAll().stream()
            .map(this::layoutResponse)
            .collect(Collectors.toList()));
    }

    public LayoutResponse updateLayout(String id, LayoutRequest request) {
        Layout layout = findById(id);
        layout.update(request.getName(), products(request.getProducts()));
        return layoutResponse(layout);
    }

    public void deleteLayout(String id) {
        Layout layout = findById(id);
        layout.emptyProducts();
        layoutRepository.delete(layout);
    }


    private Layout findById(String id) {
        return layoutRepository.findById(id)
            .orElseThrow(() -> new LayoutNotFoundException(LAYOUT_NOT_FOUND_EXCEPTION + id));
    }

    private LayoutResponse layoutResponse(Layout layout) {
        return LayoutResponse.of(layout,
            ProductResponses.of(layout.getProducts().stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList())
            ));
    }

    private List<Product> products(List<String> products) {
        return products.stream()
            .map(productId -> productService.findById(productId))
            .collect(Collectors.toList());
    }
}
