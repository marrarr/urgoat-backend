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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/register",
                    "/verify",
                    "/login",
                    "/images/**",
                    "/css/**",
                    "/js/**"
                ).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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