package com.example.springtest2.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service; // созданый класс службы аутентификации

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRespons> register ( // метод возвращает ответ аутентификации
            @RequestBody RegisterRequest request) { // в аргументе получаем регистрационную информацию
        return ResponseEntity.ok(service.register(request)); // вызываем у службы аутентификации метод регистрации
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationRespons> authenticate ( // метод возвращает ответ аутентификации
            @RequestBody AuthenticationRequest request) { // в аргументе получаем информацию аутентификации
        return ResponseEntity.ok(service.authenticate(request)); // вызываем у службы аутентификации метод аутентификации
    }

}
