package kr.co.greetech.back.business.login.jwt.controller;

import io.jsonwebtoken.ExpiredJwtException;
import kr.co.greetech.back.dto.CompanyCreateDto;
import kr.co.greetech.back.business.login.jwt.util.JwtRequest;
import kr.co.greetech.back.business.login.jwt.util.JwtResponse;
import kr.co.greetech.back.business.login.jwt.util.JwtTokenUtil;
import kr.co.greetech.back.business.login.jwt.service.JwtUserDetailsService;
import kr.co.greetech.back.dto.CompanyReadDto;
import kr.co.greetech.back.entity.Company;
import kr.co.greetech.back.util.ExceptionMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Profile("local")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public Long signup(@Validated CompanyCreateDto companyCreateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

        return userDetailsService.create(companyCreateDto);
    }

    @PostMapping(value = "/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        final Long companyId = userDetailsService.getCompanyId(authenticationRequest.getUsername());
        log.info("token: " + token);
        return new JwtResponse(token, companyId);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("update/{companyId}")
    public Long update(
            @PathVariable Long companyId,
            @Validated CompanyReadDto companyReadDto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            log.warn("error={} ", bindingResult);
            throw new IllegalArgumentException(ExceptionMsg.bindingMsg(bindingResult));
        }

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

        return userDetailsService.update(companyId, username, companyReadDto);
    }
}