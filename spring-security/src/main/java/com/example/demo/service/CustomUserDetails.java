package com.example.demo.service;

import com.example.demo.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @ClassName CustomUserDetails
 * @auther haoshidi
 * @date 2022/4/8 8:09
 * @Description
 * @Version 1.0
 */
public class CustomUserDetails implements UserDetails {
    private  final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }
    public final User getUser(){
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorityList().stream().map(a -> new SimpleGrantedAuthority(a.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
