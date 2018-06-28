package com.github.userportal.config;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    // The DefaultTokenServices bean provided at the AuthorizationConfig
    private final DefaultTokenServices tokenServices;

    // The TokenStore bean provided at the AuthorizationConfig
    private final TokenStore tokenStore;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceIds)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
//        http
//                .requestMatchers()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("http://localhost:8080/oauth/token").permitAll()
//                .antMatchers("/user-portal/{username}/{password}").permitAll()
//                .antMatchers("/user-portal/**").authenticated()
//                .antMatchers("/user-portal/users" ).hasAuthority("admin");

        httpSecurity
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**", "/login").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

    }
}
