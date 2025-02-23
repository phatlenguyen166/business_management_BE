package vn.bookstore.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Instant lastLogin;
    private Instant createdAt;
    private Instant updatedAt;
    private String refreshToken;
    private int status;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private User user;

}
