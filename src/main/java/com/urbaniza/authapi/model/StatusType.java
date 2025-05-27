package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status_types")
public class StatusType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 20, unique = true)
  private String name;

  protected StatusType(){};
  public StatusType(String name) {
    validateName(name);
    this.name = name.trim();
  }

  // --- Getters & Setters---

  public Long getId() {return id;}

  public String getName() {return name;}
  public void setName(String name) {
    validateName(name);
    this.name = name.trim();
  }

  // --- Helper methods---

  private void validateName(String name) {
    Objects.requireNonNull(name, "O nome do tipo de status não pode ser nulo.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do tipo de status não pode ser vazio.");
    }
  }


  // --- equals(), hashCode() e toString() ---

}