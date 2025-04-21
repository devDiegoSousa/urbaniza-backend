package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByUserId(Integer userId);

    List<Report> findByStatus(String status);

    // Você pode adicionar mais métodos de consulta conforme necessário
    // Exemplo:
    // List<Report> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLng, Double maxLng);
}