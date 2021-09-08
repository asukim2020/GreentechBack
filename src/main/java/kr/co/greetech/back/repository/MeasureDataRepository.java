package kr.co.greetech.back.repository;

import kr.co.greetech.back.entity.MeasureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasureDataRepository extends JpaRepository<MeasureData, Long> {
    //    List<MeasureData> findAllByCreatedTimeBetween(LocalDateTime start, LocalDateTime end);

    // TODO: - test 코드 => 지울 것
    List<MeasureData> findAllByDataLoggerId(Long dataLoggerId);
}
