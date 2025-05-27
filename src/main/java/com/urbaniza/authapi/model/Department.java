package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email; // Para validação de email

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "departments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_department_name_city", columnNames = {"name", "city_id"})
    })
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Email(message = "O formato do email é inválido.")
  @Column(name = "email", length = 50)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "fk_department_city"))
  private City city;

  @OneToMany(
      mappedBy = "department",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  private List<Segment> segments = new ArrayList<>();

  protected Department() {}
  public Department(String name, String email, City city) {
    Objects.requireNonNull(name, "O nome do departamento não pode ser nulo.");
    Objects.requireNonNull(city, "A cidade do departamento não pode ser nula.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do departamento não pode ser vazio.");
    }

    this.name = name.trim();
    this.email = email;
    this.city = city;
  }

  // --- Getters & Setters---

  public Long getId() {return id;}

  public String getName() {return name;}
  public void setName(String name) {
    Objects.requireNonNull(name, "O nome do departamento não pode ser nulo.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do departamento não pode ser vazio.");
    }
    this.name = name.trim();
  }

  public String getEmail() {return email;}
  public void setEmail(String email) {this.email = email;}

  public City getCity() {return city;}
  protected void setCity(City city) {this.city = city;}


  public List<Segment> getSegments() {return segments;}
  public void setSegments(List<Segment> segments) {this.segments = segments;}


  // --- Helper methods---

  public void addSegment(Segment segment) {
    if (this.segments == null) {
      this.segments = new ArrayList<>();
    }
    this.segments.add(segment);
    segment.setDepartment(this);
  }

  public void removeSegment(Segment segment) {
    if (this.segments != null) {
      this.segments.remove(segment);
      segment.setDepartment(null);
    }
  }


  // --- equals(), hashCode() e toString() ---

  @Override
  public String toString() {
    return "Department{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", cityId=" + (city != null ? city.getId() : "null") +
        '}';
  }
}