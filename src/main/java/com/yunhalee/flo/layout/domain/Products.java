package com.yunhalee.flo.layout.domain;

import com.yunhalee.flo.product.domain.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Products {

    @OneToMany(mappedBy = "product")
    private List<Product> products = new ArrayList<>();

    public Products(List<Product> products) {
        this.products = products;
    }
}
