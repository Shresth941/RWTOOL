package RwTool.rwtool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true) // enable @PreAuthorize
public class SecurityConfig {

    // If you have a custom UserDetailsService bean, Spring will auto-wire it.
    // Example: private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests(authorize -> authorize
                // allow swagger and openapi
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // optionally allow health endpoints if you added actuator
                .requestMatchers("/actuator/**").permitAll()
                // allow unauthenticated ingest upload if you want source system to upload without auth:
                // .requestMatchers("/api/ingest/upload").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(); // Basic auth for simplicity

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
