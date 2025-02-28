package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "seniority_levels")
@Getter
@Setter
public class SeniorityLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int level;
    private String levelName;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private int salaryCoefficient;

    @OneToMany(mappedBy = "seniorityLevel")
    private List<User> users;
}
