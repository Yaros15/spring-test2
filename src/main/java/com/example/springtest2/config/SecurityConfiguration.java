package com.example.springtest2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider; // бин из созданного класса ApplicationConfig

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception { //метод с фозвроащаемым значениме цепочка фильтров безопасности
        http
                .csrf()
                .disable() // отключаем csrf
                .authorizeHttpRequests() // после авторизации
                //.requestMatchers("api/v1/auth") //средства сопастовления запросов (список сторк/шаблонов претставляющий приложения) - открываем доступ к этому адресу
                .antMatchers("/**")
                .permitAll() // разрешить все запросы для этого списка
                .anyRequest() // но любой другой запрос
                .authenticated()// должны быть аутентифицированы
                .and() // и посмотрим как можем настроить сеанс
                .sessionManagement() // управление сиансами
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // в политике создания сеансов (делаем сеанс без состояния) - что бы фильтр выполнялся один раз за запрос
                .and() // и
                .authenticationProvider(authenticationProvider) // используем поставщика аутентификации(передаем объект провайдера аутентификации)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // метод добавления фильтра перед: jwtAuthFilter,

        return http.build(); //вернем сборнку http безопасности
    }
}
