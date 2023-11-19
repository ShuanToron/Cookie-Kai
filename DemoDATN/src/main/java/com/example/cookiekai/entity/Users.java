package com.example.cookiekai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    @NotNull(message = "Email cannot be blank")
    private String email;

    @Column(length = 64, nullable = false)
    @NotNull(message = "Password cannot be blank")
    private String password;

    @Column(length = 100, nullable = false)
    @NotNull(message = "Fullname cannot be blank")
    private String fullname;

    @Column(length = 64)
    private String photos;

    @Column(length = 100, nullable = false)
    @NotNull(message = "Address cannot be blank")
    private String address;

    @Column(length = 10, nullable = false, unique = true)
    @NotNull(message = "Phonenumber cannot be blank")
    private String phoneNumber;

    @Column(name = "birthday")
    @NotNull(message = "Birthday cannot be blank")
    private LocalDate birthday;

    @Column(nullable = false)
    @NotNull(message = "Gender cannot be blank")
    private Boolean gender;

    @NotNull(message = "Enabled cannot be blank")
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role", referencedColumnName = "id")
    private Roles roles;

    @Transient
    public String getImagePath() {
        if (id == null || photos == null) return "/dashboard/assets/img/img.png";
        return "/user-photos" + "/" + this.id + "/" + this.photos;
    }
}
