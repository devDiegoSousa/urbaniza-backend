package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "city_id"})
})
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "email", length = 50, unique = true)
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id", nullable = false)
  private City city;

  @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Segment> segments = new ArrayList<>();

  // Constructors
  public Department() {}

  public Department(String name, String email, City city) {
    this.name = name;
    this.email = email;
    this.city = city;
  }

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public String getEmail() {return email;}
  public void setEmail(String email) {this.email = email;}

  public City getCity() {return city;}
  public void setCity(City city) {this.city = city;}

  public List<Segment> getSegments() {return segments;}

  public void setSegments(List<Segment> segments) {this.segments = segments;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Department that = (Department) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "Department{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", cityId=" + (city != null ? city.getId() : null) +
        '}';
  }
}