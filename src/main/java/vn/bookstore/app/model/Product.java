package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private int quantity;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<BillDetail> billDetails;
}
