package com.urbaniza.authapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "segments",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_segment_name_department", columnNames = {"name", "department_id"})
    })
public class Segment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "department_id", nullable = false, foreignKey = @ForeignKey(name = "fk_segment_department"))
  private Department department;

  @OneToMany(
      mappedBy = "segment",
      cascade = { CascadeType.PERSIST, CascadeType.MERGE }, // Apenas propaga persistência e merge
      orphanRemoval = false, // Não remove denúncias se forem desassociadas
      fetch = FetchType.LAZY
  )
  private List<Report> reports = new ArrayList<>();

  protected Segment() {}
  public Segment(String name, Department department) {
    Objects.requireNonNull(name, "O nome do segmento não pode ser nulo.");
    Objects.requireNonNull(department, "O departamento do segmento não pode ser nulo.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do segmento não pode ser vazio.");
    }

    this.name = name.trim();
    this.department = department;
  }

  // --- Getters & Setters---

  public Long getId() {return id;}

  public String getName() {return name;}
  public void setName(String name) {
    Objects.requireNonNull(name, "O nome do segmento não pode ser nulo.");
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome do segmento não pode ser vazio.");
    }
    this.name = name.trim();
  }

  public Department getDepartment() {return department;}
  protected void setDepartment(Department department) {this.department = department;}


  public List<Report> getReports() {return reports;}
  public void setReports(List<Report> reports) {this.reports = reports;}

  // --- Helper methods ---

  public void addReport(Report report) {
    if (this.reports == null) {
      this.reports = new ArrayList<>();
    }
    this.reports.add(report);
    report.setSegment(this);
  }

  public void removeReport(Report report) {
    if (this.reports != null) {
      this.reports.remove(report);
      report.setSegment(null); // Assume que Report tem um setSegment
    }
  }


  // --- equals(), hashCode() e toString() ---

  @Override
  public String toString() {
    return "Segment{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", departmentId=" + (department != null ? department.getId() : "null") +
        '}';
  }
}