package fi.sdeska.citybike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/").permitAll()
            .requestMatchers("/stations").permitAll()
            .requestMatchers("/journeys").permitAll()
            .requestMatchers("/station/*").permitAll()
            .requestMatchers("/journey/*").permitAll();
        return http.build();
    }
    
}
