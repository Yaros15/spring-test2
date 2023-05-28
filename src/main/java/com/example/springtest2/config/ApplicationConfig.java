package com.example.springtest2.config;

import com.example.springtest2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){ // создаем метод, который возвращает сведения о пользователи
        return username -> userRepository.findByEmail(username) // через лямбду, переопределяем к методу интерфейса: loadUserByUsername
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // если логина нет, тогда выпрасываем через лямбду, оюъект ошибки
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){ // поставщик аутентификации
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // объект доступа к данным, который отвечает за извлечение данных
        authProvider.setUserDetailsService(userDetailsService()); // устанавливаем поставщику аутентификации какую службу сведений о пользователе ему использовать
        authProvider.setPasswordEncoder(passwordEncoder()); //устанавливаем поставщику аутентификации какой кодировщик паролей ему использовать
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception { // менеджер аутентификации
        return config.getAuthenticationManager(); // возвращаем состояние менеджера аутентификации и конфигурации
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // кодировщик паролей
        return new BCryptPasswordEncoder();
    }

}
