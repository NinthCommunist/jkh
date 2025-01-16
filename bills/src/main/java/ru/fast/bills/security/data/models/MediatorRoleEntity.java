package ru.fast.bills.security.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.fast.bills.data.models.MediatorEntity;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "mediator_roles", schema = "bills")
public class MediatorRoleEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private MediatorAuthority authority;

    @ManyToMany
    @JoinTable(schema = "bills", name = "mediator2roles",
            inverseJoinColumns = @JoinColumn(name = "mediator_id"),
            joinColumns = @JoinColumn(name = "role_id"))
    private Collection<MediatorEntity> mediator;
}
