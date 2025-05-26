package com.urbaniza.authapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "cities")
public class City {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotBlank (message = "City must have a name")
  @Size(max = 50, message = "Name cannot exceed 50 characters")
  @Column(nullable = false)
  private String name;
  @NotBlank (message = "City must have a name")
  @Size(max = 2, message = "uf cannot exceed 2 characters")
  @Column(nullable = false)
  private String uf;

  // Constructors to JPA
  public City(){}
  public City(Long id, String name, String uf){
    this.id = id;
    this.name = name;
    this.uf = uf;
  }

  // Getters and setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getName() {return name;}
  public void setName(String name) {this.name = name;}

  public String getUf() {return uf;}
  public void setUf(String uf) {this.uf = uf;}
}
