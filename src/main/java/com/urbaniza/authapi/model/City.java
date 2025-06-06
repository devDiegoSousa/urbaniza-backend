package com.urbaniza.authapi.model; // Ajuste o pacote conforme sua estrutura

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cities", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "uf"})
})
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "uf", nullable = false, length = 2)
  private String uf;

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Department> departments = new ArrayList<>();

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Region> regions = new ArrayList<>();

  // Constructors
  public City() {}
  public City(String name, String uf) {
    this.name = name;
    this.uf = uf;
  }

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public String getUf() {return uf;}
  public void setUf(String uf) {this.uf = uf;}

  public List<Department> getDepartments() {return departments;}

  public void setDepartments(List<Department> departments) {this.departments = departments;}

  public List<Region> getRegions() {return regions;}
  public void setRegions(List<Region> regions) {this.regions = regions;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    City city = (City) o;
    return Objects.equals(id, city.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "City{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", uf='" + uf + '\'' +
        '}';
  }
}