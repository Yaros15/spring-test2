package com.example.springtest2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { //Фильтр аутентификации унаследованый от класса Один раз для каждого фильтра запросов

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, // Извлекать данные из запроса
                                    @NonNull HttpServletResponse response, // Предоставлять данные в ответе
                                    @NonNull FilterChain filterChain // Цепочка фильтров
                                    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Заголовок аутентификации
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){ // проверка на пустоту и на то что маркер сборки не начинается с слова Bearer
            filterChain.doFilter(request, response); // вызываем цепучку фильтров и передаем запрос и ответ следующему фильтру
            return; // выходим
        }
        jwt = authHeader.substring(7); // извлекаем токен из загаловка авторизации начиная с позиции номер 7, минуя слово с пробелом - "Bearer "
        userEmail = jwtService.extractUsername(jwt); //извлекаем логин пользовтеля из токена jwt
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
