package kr.co.greetech.back.business.datalogger.repository;

import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataLoggerRepository extends JpaRepository<DataLogger, Long> {
    List<DataLogger> findByCompanyId(Long companyId);

    @Query("select d.id from DataLogger d where d.company.id = :id")
    List<Long> findAllByCompanyId(@Param("id") Long companyId);
}
