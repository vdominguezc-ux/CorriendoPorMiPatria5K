package com.nuevafrecuencia.patria5kweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "participants", uniqueConstraints = @UniqueConstraint(columnNames = "bib_number"))
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bib_number", length = 24, unique = true)
    private String bibNumber;

    @NotBlank(message = "Escribe el nombre completo")
    @Size(max = 120, message = "El nombre es demasiado largo")
    @Column(nullable = false, length = 120)
    private String name;

    @Min(value = 5, message = "La edad mínima es 5 años")
    @Max(value = 100, message = "Verifica la edad")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Escribe el municipio")
    @Size(max = 100, message = "El municipio es demasiado largo")
    @Column(nullable = false, length = 100)
    private String municipality;

    @NotBlank(message = "Escribe el número telefónico")
    @Size(max = 30, message = "El teléfono es demasiado largo")
    @Column(nullable = false, length = 30)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBibNumber() { return bibNumber; }
    public void setBibNumber(String bibNumber) { this.bibNumber = bibNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getMunicipality() { return municipality; }
    public void setMunicipality(String municipality) { this.municipality = municipality; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
