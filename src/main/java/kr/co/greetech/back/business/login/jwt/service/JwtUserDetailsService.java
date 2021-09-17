package kr.co.greetech.back.business.login.jwt.service;

import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public Long create(CompanyCreateDto companyCreateDto) {
        Company company = Company.create(companyCreateDto);
        companyRepository.save(company);
        return company.getId();
    }

    public Long getCompanyId(String username) {
        Company company = companyRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return company.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Company company = companyRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (company.getLoginId().equals(username)) {
            String pw = company.getLoginPw();
            String encodePw = passwordEncoder.encode(pw);

            return new User(company.getLoginId(), encodePw, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}