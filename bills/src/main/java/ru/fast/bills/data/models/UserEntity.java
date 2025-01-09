package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Size(min = 3)
    private String nickname;

    @NotBlank
    @Size(min = 3)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
