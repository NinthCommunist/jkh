package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import ru.fast.bills.data.listeners.CreatedByMediator;
import ru.fast.bills.data.listeners.CreatedByMediatorEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hibernate.annotations.UuidGenerator.Style.TIME;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "bills")
@EntityListeners({CreatedByMediatorEntityListener.class})
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false)
    @UuidGenerator(style = TIME)
    @GeneratedValue
    private UUID id;

    @Size(min = 0, max = 10)
    private String phone;

    @NotBlank
    @Size(min = 3)
    @Column(unique = true)
    private String nickname;

    @CreatedByMediator
    private String createdByService;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
