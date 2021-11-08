package kr.co.greetech.back.business.login.jwt.service;

import kr.co.greetech.back.business.login.jwt.repository.FcmTokenRepository;
import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.dto.CompanyReadDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.entity.FcmToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("local")
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final FcmTokenRepository fcmTokenRepository;

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

    @Transactional
    public Long addFcmToken(Long companyId, String fcmToken) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with companyId: " + companyId));

        Optional<FcmToken> fcmTokenOptional = fcmTokenRepository.findAllByCompanyIdAndFcmToken(companyId, fcmToken);
        if (fcmTokenOptional.isPresent()) {
            FcmToken token = fcmTokenOptional.get();
            token.updateFcmToken(fcmToken);
        } else {
            FcmToken fcmTokenObj = FcmToken.create(company, fcmToken);
            fcmTokenRepository.save(fcmTokenObj);
        }

        List<FcmToken> removeFcmTokenList = fcmTokenRepository.findAllByCompanyIdAndLastModifiedTimeLessThan(companyId, LocalDateTime.now().minusDays(14L));
        fcmTokenRepository.deleteAll(removeFcmTokenList);
        return company.getId();
    }

    @Transactional
    public Long update(Long companyId, String username, CompanyReadDto companyReadDto) throws UsernameNotFoundException {
        Company authCompany = companyRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with companyId: " + companyId));

        if (!authCompany.getId().equals(company.getId())) {
            throw new UsernameNotFoundException("Unauthorized");
        }

        company.update(companyReadDto);
        return company.getId();
    }
}