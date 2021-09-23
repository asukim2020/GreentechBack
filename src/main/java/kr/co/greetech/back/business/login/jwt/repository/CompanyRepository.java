package kr.co.greetech.back.business.login.jwt.repository;

import kr.co.greetech.back.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    List<Company> findByLoginIdAndLoginPw(String loginId, String loginPw);
    Optional<Company> findByLoginId(String loginId);
}
