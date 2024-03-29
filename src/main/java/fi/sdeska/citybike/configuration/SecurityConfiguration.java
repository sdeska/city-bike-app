package fi.sdeska.citybike.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class contains configuration related to Spring Security.
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Specifies allowed operations for users.
     * @param http for configuring web based security.
     * @return the built HttpSecurity object.
     * @throws Exception when an error occurs during building the HttpSecurity object.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorizeHttpRequests -> 
            authorizeHttpRequests
            .requestMatchers("/", "/stations", "/journeys", "/station", "/journey", "/coffee", "/map")
            .permitAll()).build();
    }
    
}
