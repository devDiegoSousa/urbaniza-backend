package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


@Entity
@Table(name = "regions",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_region_name_city", columnNames = {"name", "city_id"})
    })
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  /**
   * - optional = false: Garante que uma Região *sempre* deve ter uma Cidade associada.
   * - @JoinColumn: Especifica a coluna da chave estrangeira ('city_id').
   * - foreignKey: Define um nome explícito para a constraint no banco,
   * o que é bom para a manutenção do schema.
   */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(name = "fk_region_city"))
  private City city;

  protected Region() {}
  public Region(String name, City city) {
    Objects.requireNonNull(name, "O nome da região não pode ser nulo.");
    Objects.requireNonNull(city, "A cidade da região não pode ser nula.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome da região não pode ser vazio.");
    }

    this.name = name.trim();
    this.city = city;
  }

  // --- Getters & Setters---

  public Long getId() {return id;}

  public String getName() {return name;}
  public void setName(String name) {
    Objects.requireNonNull(name, "O nome da região não pode ser nulo.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome da região não pode ser vazio.");
    }
    this.name = name.trim();
  }

  public City getCity() {return city;}
  protected void setCity(City city) {this.city = city;}



  // --- equals() e hashCode() ---
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Region region = (Region) o;
    return id != null && id.equals(region.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  // --- toString() ---

  @Override
  public String toString() {
    return "Region{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", cityId=" + (city != null ? city.getId() : "null") +
        '}';
  }
}