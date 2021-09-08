package kr.co.greetech.back.repository;

import kr.co.greetech.back.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByName(String name);
    List<Company> findByLoginIdAndLoginPw(String loginId, String loginPw);
}
