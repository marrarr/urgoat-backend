package demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import demo.security.repository.UserRepository;

/**
 * Klasa konfiguracyjna dla zabezpieczeń aplikacji przy użyciu Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Konfiguruje łańcuch filtrów zabezpieczeń dla żądań HTTP.
     *
     * @param http Obiekt HttpSecurity do konfiguracji zabezpieczeń
     * @return Skonfigurowany obiekt SecurityFilterChain
     * @throws Exception W przypadku błędów konfiguracji
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/register",
                                "/verify",
                                "/login",
                                "/dodaj_post",
                                "/images/**",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/redirectAfterLogin").authenticated()
                        .anyRequest().hasRole("USER")

                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/redirectAfterLogin", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Tworzy bean kodera haseł używającego algorytmu BCrypt.
     *
     * @return Obiekt PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Konfiguruje serwis szczegółów użytkownika dla uwierzytelniania.
     *
     * @param userRepository Repozytorium użytkowników
     * @return Obiekt UserDetailsService do ładowania danych użytkownika
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            System.out.println("Probowanie zaladowania uzytkownika: " + username);
            return userRepository.findByUsername(username)
                    .map(user -> {
                        System.out.println("Znaleziony uzytkownik: " + user.getUsername() + ", rola: " + user.getRole());
                        return new org.springframework.security.core.userdetails.User(
                                user.getUsername(),
                                user.getPassword(),
                                java.util.Collections.singletonList(
                                        new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole())
                                )
                        );
                    })
                    .orElseThrow(() -> {
                        System.out.println("Nieznaleziony uzytkownik: " + username);
                        return new UsernameNotFoundException("Uzytkownika nie znaleziono");
                    });
        };
    }
}