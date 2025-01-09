package ru.fast.bills.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "executor", schema = "bills")
public class ExecutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(min = 0, max = 10)
    private String phone;

    @NotBlank
    @Size(min = 3)
    private String name;

    private String organization;

}
