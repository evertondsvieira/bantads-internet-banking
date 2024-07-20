package bantads.msclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings({ "removal", "deprecation" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .requestMatchers(HttpMethod.GET, "/api/client/**").permitAll() 
        .requestMatchers(HttpMethod.POST, "/api/client/**").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/client/**").permitAll() 
        .requestMatchers(HttpMethod.DELETE, "/api/client/**").permitAll()
        .anyRequest().authenticated();
        return http.build();
    }
}
