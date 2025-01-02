package ru.fast.bills.dao.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "bills")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Email
    private String email;

    @NotBlank
    @Min(value = 3)
    private String nickname;

    @NotBlank
    @Min(value = 3)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ClaimEntity> claims;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
