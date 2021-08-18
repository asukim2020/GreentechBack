package kr.co.greetech.back.repository;

import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataLoggerRepository extends JpaRepository<DataLogger, Long> {
    List<DataLogger> findByCompanyId(Long companyId);
}
