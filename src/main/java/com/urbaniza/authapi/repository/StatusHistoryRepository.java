package com.urbaniza.authapi.repository;

import com.urbaniza.authapi.model.Report;
import com.urbaniza.authapi.model.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {


  @Query("SELECT sh FROM StatusHistory sh WHERE sh.report = :report ORDER BY sh.modifiedOn DESC")
  List<StatusHistory> findAllByReport(@Param("report") Report report);

}
