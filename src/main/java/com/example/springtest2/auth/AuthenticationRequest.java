package com.example.springtest2.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest { // простой класс для хранения информации необходимой для авторизации

    private String email;
    String password;
}
