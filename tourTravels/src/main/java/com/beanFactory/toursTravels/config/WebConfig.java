package com.beanFactory.toursTravels.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebConfig {

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/register", "/auth/check-username", "/auth/login").permitAll()
                                .anyRequest().authenticated()
                        )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .logout(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        System.out.println(passwordEncoder().encode("pass123"));
//        UserDetails user = User.withUsername("chetan")
//                .password(passwordEncoder().encode("pass123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new AppUserDetailsService();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager aUthenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
