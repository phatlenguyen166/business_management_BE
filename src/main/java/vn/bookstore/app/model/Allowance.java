package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "allowances")
@Getter
@Setter
public class Allowance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal allowance;

    @OneToMany(mappedBy = "allowance")
    private List<Role> roles;
}

