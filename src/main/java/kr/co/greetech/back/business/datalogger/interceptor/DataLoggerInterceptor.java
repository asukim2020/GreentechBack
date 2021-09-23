package kr.co.greetech.back.business.datalogger.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.business.login.jwt.util.JwtTokenUtil;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.entity.DataLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Profile("local")
@Slf4j
@RequiredArgsConstructor
public class DataLoggerInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyRepository companyRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        String[] split = path.split("/");
        path = split[split.length - 1];

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = "null";
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.warn("JWT Token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        Company company = companyRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unauthorized"));
        Long companyId = company.getId();
        long pathCompanyId = Long.parseLong(path);

        if (companyId == pathCompanyId) {
            return true;
        } else {
            throw new UsernameNotFoundException("Unauthorized");
        }
    }
}
