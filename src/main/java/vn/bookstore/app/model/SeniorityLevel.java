package vn.bookstore.app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "seniority_levels")
public class SeniorityLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int level;
    private String levelName;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private int salaryCoefficient;

    @OneToMany(mappedBy = "seniorityLevel")
    private List<User> users;
}
