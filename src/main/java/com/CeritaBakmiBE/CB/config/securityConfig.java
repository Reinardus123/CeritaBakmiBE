package com.CeritaBakmiBE.CB.config;

import com.CeritaBakmiBE.CB.repository.UserRepository;
import io.jsonwebtoken.security.Password;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class securityConfig {

    private final UserRepository userRepository;
    private final securityJwtFilter securityJwtFilter;

    public securityConfig(UserRepository userRepository, securityJwtFilter securityJwtFilter) {
        this.userRepository = userRepository;
        this.securityJwtFilter = securityJwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return ((request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setHeader("WWW-Authenticate", "");
            response.getWriter().write("{\"error\":\"Unauthorized Access\"}");
        });
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(){

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint()))
                .authorizeHttpRequests(configurer ->
                        configurer
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**",
                                "/swagger-resources/**", "/webjars/**" , "/docs", "/uploads/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/menu/getMenu").permitAll()

                                .requestMatchers(HttpMethod.PUT, "/api/menu/{id}/updateStatus").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/menu/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/menu/createMenu").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/menu/update/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/category/getCat").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/category/createCat").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/category/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/category/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/category/category/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/auth/login").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/auth/register").hasRole("ADMIN")


                                .requestMatchers(HttpMethod.POST, "/api/branch/createbranch").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/branch/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/branch/update/{id}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/api/updateStatus/{id}/updatePaymentStatus").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/updateStatus/{id}/updateOrderStatus").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/api/checkout/getByBranch").hasRole("ADMIN")


                        .anyRequest().authenticated()
                        ).addFilterBefore(securityJwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
