package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "good_receipt_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GoodReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal inputPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_receipt_id", nullable = false)
    private GoodReceipt goodReceipt;

}
