package kr.co.greetech.back.business.login.jwt.repository;

import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findAllByCompanyIdAndFcmToken(Long companyId, String fcmToken);
    List<FcmToken> findAllByCompanyIdAndLastModifiedTimeLessThan(Long companyId, LocalDateTime lastModifiedTime);
    List<FcmToken> findAllByCompanyId(Long companyId);
}
