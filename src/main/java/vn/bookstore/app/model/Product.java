package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private int quantity;
    private int status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private List<BillDetail> billDetails;
    
    @OneToMany(mappedBy = "product")
    private List<GoodReceiptDetail> goodReceiptDetails;
}
