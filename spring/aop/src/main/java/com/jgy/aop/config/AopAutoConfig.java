package com.jgy.aop.config;

import com.jgy.aop.aspect.AnnotaAdvisers;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class AopAutoConfig {

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(
            ServerHttpSecurity http) {
        /*return http.authorizeExchange()
                .anyExchange().authenticated()
                .and().build();*/
        http.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/hello")
                .hasRole("ADMIN")
                .pathMatchers("/string/hello")
                .permitAll()
                .and()
                .httpBasic().disable()
                .cors().disable()
                .logout().disable();
        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AnnotaAdvisers getAnnotaAdvisers() {
        return new AnnotaAdvisers();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
