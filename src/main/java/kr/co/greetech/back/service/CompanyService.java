package kr.co.greetech.back.service;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.repository.CompanyRepository;
import kr.co.greetech.back.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Long create(CompanyCreateDto companyCreateDto) {
        Company company = Company.create(companyCreateDto);
        companyRepository.save(company);
        return company.getId();
    }

    public Long login(String id, String pw, HttpServletRequest request) {
        List<Company> companies = companyRepository.findByLoginIdAndLoginPw(id, pw);
        if (companies.isEmpty()) throw new IllegalArgumentException("can't found data");

        Long companyId = companies.get(0).getId();
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_COMPANY, companyId);

        return companyId;
    }
}
