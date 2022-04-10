package com.example.demo.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @ClassName AdditionalClaimsAccessTokenConverter
 * @auther haoshidi
 * @date 2022/4/10 16:41
 * @Description
 * @Version 1.0
 */
public class AdditionalClaimsAccessTokenConverter extends JwtAccessTokenConverter {
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {

        OAuth2Authentication authentication = super.extractAuthentication(map);
        authentication.setDetails(map);

        return authentication;
    }
}
