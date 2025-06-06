package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "segments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "department_id"})
})
public class Segment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  // Constructors
  public Segment() {}

  public Segment(String name, Department department) {
    this.name = name;
    this.department = department;
  }

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public Department getDepartment() {return department;}
  public void setDepartment(Department department) {this.department = department;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Segment segment = (Segment) o;
    return Objects.equals(id, segment.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "Segment{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", departmentId=" + (department != null ? department.getId() : null) +
        '}';
  }
}