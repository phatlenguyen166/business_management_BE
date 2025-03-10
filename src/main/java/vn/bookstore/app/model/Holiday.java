package vn.bookstore.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
@Getter
@Setter
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên ngày lễ không được để trống")
    private String name;
    @NotNull(message = "Start date không được để trống")
    @FutureOrPresent(message = "Start date phải là ngày hiện tại hoặc tương lai")
    private LocalDate startDate;
    @NotNull(message = "End date không được để trống")
    @FutureOrPresent(message = "End date phải là ngày hiện tại hoặc tương lai")
    private LocalDate endDate;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private Integer status;
}
