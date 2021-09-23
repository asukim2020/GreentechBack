package kr.co.greetech.back;

import kr.co.greetech.back.business.datalogger.interceptor.DataLoggerInterceptor;
import kr.co.greetech.back.business.datalogger.repository.DataLoggerRepository;
import kr.co.greetech.back.business.login.jwt.repository.CompanyRepository;
import kr.co.greetech.back.business.login.jwt.util.JwtTokenUtil;
import kr.co.greetech.back.business.measuredata.interceptor.MeasureDataInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("local")
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyRepository companyRepository;
    private final DataLoggerRepository dataLoggerRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DataLoggerInterceptor(jwtTokenUtil, companyRepository))
                .order(2)
                .addPathPatterns("/dataLogger/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new MeasureDataInterceptor(jwtTokenUtil, companyRepository, dataLoggerRepository))
                .order(1)
                .addPathPatterns("/measureData/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
    }
}
