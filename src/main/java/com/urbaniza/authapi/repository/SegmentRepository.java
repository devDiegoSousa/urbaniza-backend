package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SegmentRepository extends JpaRepository<Segment, Long>{
  List<Segment> findByDepartmentId(Long departmentId);
}
