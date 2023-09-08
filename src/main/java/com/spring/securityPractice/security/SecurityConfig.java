package com.spring.securityPractice.security;

import com.spring.securityPractice.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;




import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.GET, "/users/hello").hasRole("user")
                            .requestMatchers(HttpMethod.GET, "/users/**").hasRole("admin")
                            .requestMatchers(HttpMethod.GET, "/users/profile").hasRole("user")
                            .requestMatchers(HttpMethod.GET, "/users/posts").hasRole("user")
                            .requestMatchers(HttpMethod.POST, "/users/comment").hasRole("user")
                            .requestMatchers(HttpMethod.POST, "/admin/create-post/**").hasRole("admin")
                            .requestMatchers(HttpMethod.DELETE, "/admin/delete-post/**").hasRole("admin")
                            .requestMatchers(HttpMethod.GET, "/admin/view-reports").hasRole("admin")
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/login");
                })
        ;
        return http.build();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/public/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/custom-login")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/custom-login")
//                .permitAll();
//    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}




