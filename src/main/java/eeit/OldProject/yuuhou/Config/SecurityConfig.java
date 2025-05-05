package eeit.OldProject.yuuhou.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import eeit.OldProject.yuuhou.Service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    /* 🔥 AuthenticationManager 正確版 【有改】 */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /* 🔥 SecurityFilterChain 正確版 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors() // ✅ 開啟 CORS 支援
            .and()
            .csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
//                .requestMatchers("/caregiver/**").hasRole("CAREGIVER")
//                .requestMatchers("/api/caregivers").permitAll()
//                .requestMatchers("/news/**").permitAll()
//                .requestMatchers("/user/**").permitAll()
//                .requestMatchers("/category/**").permitAll()
//                .requestMatchers("/api/courses/**").permitAll()
//                .requestMatchers("/api/chapters/**").permitAll()
//                .requestMatchers("/**").permitAll()

                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
//public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .cors() // ✅ 開啟 CORS 支援
//            .and()
//            .csrf().disable()
//            .exceptionHandling()
//                .authenticationEntryPoint(entryPoint)
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//            .requestMatchers("/api/auth/**").permitAll()
//            .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
//            .requestMatchers("/api/caregivers/photo").hasRole("CAREGIVER")
//            .requestMatchers("/api/caregivers/search").permitAll()
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .requestMatchers("/caregiver/**").hasRole("CAREGIVER")
//            .requestMatchers("/user/**").hasAnyRole("USER", "CAREGIVER", "ADMIN")
//            .requestMatchers("/api/caregivers/register", "/api/caregivers/login").permitAll()
//            .requestMatchers("/news/**", "/category/**", "/api/courses/**", "/api/chapters/**").permitAll()
//
//            .anyRequest().authenticated()
//
//
//            .and()
//            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
