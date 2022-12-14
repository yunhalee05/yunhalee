package com.yunhalee.flo.layout.domain;

import com.yunhalee.flo.product.domain.Product;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
public class Layout {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Embedded
    private Products products;

    @Builder
    public Layout(String id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = new Products(products);
    }

    public void addProducts(List<Product> products) {
        this.products.addProducts(products, this);
    }

    public void update(String name, List<Product> products) {
        this.name = name;
        this.products.update(products, this);
    }

    public void emptyProducts() {
        this.products.emptyProducts();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products.getProducts();
    }


}
