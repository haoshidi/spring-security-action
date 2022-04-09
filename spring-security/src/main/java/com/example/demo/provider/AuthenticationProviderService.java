package com.example.demo.provider;

import com.example.demo.service.CustomUserDetails;
import com.example.demo.service.JpaUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName AuthenticationProviderService
 * @auther haoshidi
 * @date 2022/4/8 8:21
 * @Description
 * @Version 1.0
 */
@Slf4j
@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailService jpaUserDetailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        CustomUserDetails user = (CustomUserDetails) jpaUserDetailService.loadUserByUsername(username);
        switch (user.getUser().getAlgorithm()){
            case BCRYPT: return checkPassword(user,password,bCryptPasswordEncoder);
            case SCRYPT: return checkPassword(user,password,sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    private Authentication checkPassword(CustomUserDetails user, String password, PasswordEncoder passwordEncoder) {
        if(passwordEncoder.matches(password,user.getPassword())){
            return new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());
        }else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
