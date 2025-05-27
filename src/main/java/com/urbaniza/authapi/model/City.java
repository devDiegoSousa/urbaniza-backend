package com.urbaniza.authapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cities",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_city_name_uf", columnNames = {"name", "uf"}) // Garante que a combinação de nome e UF seja única
    })

public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "uf", nullable = false, length = 2)
  private String uf;

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Region> regions = new ArrayList<>();

  @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Department> departments = new ArrayList<>();

  // --- Constructors ---
  public City() {}
  public City(String name, String uf) {
    this.name = name;
    this.uf = uf;
  }
  public City(Long id, String name, String uf) {
    this.id = id;
    this.name = name;
    this.uf = uf;
  }

  // --- Getters e Setters ---
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public String getUf() {return uf;}
  public void setUf(String uf) {this.uf = uf;}

  public List<Region> getRegions() {return regions;}
  public void setRegions(List<Region> regions) {this.regions = regions;}

  public List<Department> getDepartments() {return departments;}
  public void setDepartments(List<Department> departments) {this.departments = departments;}

  // --- Helper methods---

  public void addRegion(Region region) {
    if (this.regions == null) {
      this.regions = new ArrayList<>();
    }
    this.regions.add(region);
    region.setCity(this);
  }

  public void removeRegion(Region region) {
    if (this.regions != null) {
      this.regions.remove(region);
      region.setCity(null);
    }
  }

  public void addDepartment(Department department) {
    if (this.departments == null) {
      this.departments = new ArrayList<>();
    }
    this.departments.add(department);
    department.setCity(this);
  }

  public void removeDepartment(Department department) {
    if (this.departments != null) {
      this.departments.remove(department);
      department.setCity(null);
    }
  }

  // --- Equals methods, hashCode e toString ---

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    City city = (City) o;
    return id != null && id.equals(city.id);
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