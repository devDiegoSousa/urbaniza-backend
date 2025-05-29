package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "regions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "city_id"})
})
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id", nullable = false)
  private City city;

  // Constructors
  public Region() {}

  public Region(String name, City city) {
    this.name = name;
    this.city = city;
  }

  // Getters & Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public City getCity() {return city;}
  public void setCity(City city) {this.city = city;}

  // equals, hashCode, toString
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region = (Region) o;
    return Objects.equals(id, region.id);
  }

  @Override
  public int hashCode() {return Objects.hash(id);}

  @Override
  public String toString() {
    return "Region{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", cityId=" + (city != null ? city.getId() : null) +
        '}';
  }
}