package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.TIME;

@Setter
@Getter
@Entity
@Table(name = "claim", schema = "bills")
public class ClaimEntity {
    @Id
    @Column(name = "id", nullable = false)
    @UuidGenerator(style = TIME)
    @GeneratedValue
    private UUID id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String definition;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", unique = true)
    private ExecutorEntity executor;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
