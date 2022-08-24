package com.yunhalee.flo.layout.domain;

import com.yunhalee.flo.product.domain.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Products {

    @OneToMany(mappedBy = "layout")
    private List<Product> products = new ArrayList<>();

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void addProducts(List<Product> products, Layout layout) {
        products.forEach(product -> {
            this.addProduct(product);
            product.toLayout(layout);
        });
    }

    private void addProduct(Product product) {
        this.products.add(product);
    }

    public void update(List<Product> products, Layout layout) {
        removeProducts(this.products.stream()
        .filter(product -> !products.contains(product))
        .collect(Collectors.toList()));

        addProducts(products.stream()
        .filter(product -> !this.products.contains(product))
        .collect(Collectors.toList()), layout);
    }

    private void removeProducts(List<Product> products) {
        for (Product product : products) {
            this.products.remove(product);
            product.toLayout(null);
        }
    }
}
