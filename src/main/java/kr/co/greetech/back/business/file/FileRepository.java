package kr.co.greetech.back.business.file;

import kr.co.greetech.back.entity.MeasureFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<MeasureFile, Long> {
    Optional<MeasureFile> findByUrl(String url);
}
