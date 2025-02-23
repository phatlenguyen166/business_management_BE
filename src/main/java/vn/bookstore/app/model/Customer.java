package vn.bookstore.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {
    @Id
    private Long id;
    private String name;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    List<Bill> bills;
}
