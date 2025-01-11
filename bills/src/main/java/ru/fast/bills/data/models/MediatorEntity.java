package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.fast.bills.security.data.models.MediatorRoleEntity;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "mediator", schema = "bills")
public class MediatorEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String serviceName;

    @NotBlank
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(schema = "bills", name = "mediator2roles",
            joinColumns = @JoinColumn(name = "mediator_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<MediatorRoleEntity> roles;
}
