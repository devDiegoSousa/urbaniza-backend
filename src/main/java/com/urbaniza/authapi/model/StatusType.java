package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status_types")
public class StatusType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true, length = 20)
  private String name;

  // Constructors
  public StatusType() {}

  public StatusType(String name) {
    this.name = name;}

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StatusType that = (StatusType) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "StatusType{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}