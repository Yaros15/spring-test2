package com.example.springtest2.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest { // простой класс для хранения информации необходимой для регистрации

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
