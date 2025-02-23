package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "good_receipts")
@Getter
@Setter
public class GoodReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goodReceipt")
    private List<GoodReceiptDetail> goodReceiptDetails;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

//    good_receipts
//    PK 	goodreipt_id
//    FK 	id_NCC
//    FK	user_id
//    total_price
//            status
//    created_at
//            updated_at








}
