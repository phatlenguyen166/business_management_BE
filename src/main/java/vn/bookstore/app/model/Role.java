package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int status;
    @OneToMany(mappedBy = "role")
    private List<SeniorityLevel> seniorityLevels;
    @ManyToOne
    @JoinColumn(name = "allowance_id")
    private Allowance allowance;
}
