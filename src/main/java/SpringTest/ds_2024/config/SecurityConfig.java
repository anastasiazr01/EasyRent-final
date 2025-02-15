package SpringTest.ds_2024.config;


import SpringTest.ds_2024.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
   @Bean
    public UserDetailsService combinedUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
                User.withUsername("owner")
                        .password(passwordEncoder.encode("password"))
                        .roles("USER", "OWNER")
                        .build(),
                User.withUsername("tenant")
                            .password(passwordEncoder.encode("password"))
                        .roles("USER", "TENANT")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER", "ADMIN")
                        .build()
        );

        return username -> {
            try {
                return userService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                return inMemoryUserDetailsManager.loadUserByUsername(username);
            }
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, @Qualifier("combinedUserDetailsService") UserDetailsService userDetailsService) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/images/**", "/js/**", "/css/**", "/register","/saveUser","/tenant/register","/owner/register").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserService userService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(combinedUserDetailsService(userService, passwordEncoder))
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }



}