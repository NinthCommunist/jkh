package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.TIME;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "bills")
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    @UuidGenerator(style = TIME)
    @GeneratedValue
    private UUID id;

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
