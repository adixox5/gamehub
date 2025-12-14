package com.gamehub.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * В ЭТОМ ПРОЕКТЕ username = email (потому что фронт логинится по email).
     * TODO EnterRealBD: если захочешь разделить username и email — добавим отдельное поле email + unique index.
     */
    @Column(nullable = false, length = 120)
    private String username;

    @Column(length = 120)
    private String displayName;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, length = 20)
    private String role = "user";

    protected User() {
    }

    public User(String username, String displayName, String passwordHash, String role) {
        this.username = username;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.role = (role == null || role.isBlank()) ? "user" : role.trim().toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }
}
