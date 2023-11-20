package com.example.cookiekai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class config {
    @Bean
    public UsersDetailService usersDetailService() {
        return new UsersDetailService();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usersDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/dashboard/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/dashboard/users/**").hasRole("Admin")
                        .requestMatchers("/dashboard/report/**").hasRole("Admin")
                        .requestMatchers("/dashboard/orders/**").hasRole("Admin")
                        .requestMatchers("/dashboard/customer/**").hasRole("Admin")
                        .requestMatchers("/dashboard/roles/**").hasRole("Admin")
                        .requestMatchers("/dashboard/roles/**").hasAnyRole("Admin", "Editor")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin.
                        loginPage("/cookie-kai/login")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/admin/users", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/cookie-kai/login")
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(10800)
                        .key("uniqueAndSecret"))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider());
        return http.build();
    }
}
