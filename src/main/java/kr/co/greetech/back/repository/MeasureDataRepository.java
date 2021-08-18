package kr.co.greetech.back.repository;

import kr.co.greetech.back.entity.MeasureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureDataRepository extends JpaRepository<MeasureData, Long> {

}
