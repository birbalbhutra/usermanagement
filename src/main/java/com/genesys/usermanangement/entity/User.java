package com.genesys.usermanangement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * User Entity/Table created under usermanagement DB
 * Lombok used for simplifying the class
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login")
    private LocalDate lastLogin;

    @Column(name = "role")
    private String role;

}
