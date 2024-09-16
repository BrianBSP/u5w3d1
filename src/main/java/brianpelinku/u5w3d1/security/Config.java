package brianpelinku.u5w3d1.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // disabilito i componenti di defualt che non mi interessano
        httpSecurity.formLogin(http -> http.disable());
        httpSecurity.csrf(http -> http.disable());

        // disabilito le sessioni
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // disabilito il 401 su ogni richiesta
        httpSecurity.authorizeHttpRequests(http-> http.requestMatchers("/**").permitAll());

        return httpSecurity.build();

    }
}
