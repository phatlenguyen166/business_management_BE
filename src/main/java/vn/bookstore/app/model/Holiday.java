package vn.bookstore.app.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private int status;
}
