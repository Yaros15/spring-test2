package com.example.springtest2.auth;

import com.example.springtest2.config.JwtService;
import com.example.springtest2.model.Role;
import com.example.springtest2.model.User;
import com.example.springtest2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
//import lombok.var;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRespons register(RegisterRequest request) { // метод возвращает токин аутентификации с объектом в параметрах регистрации
        var user = // создаем объект пользователя из запроса на регистрацию
                User.builder() // вызываем строителя для объекта пользователя
                .firstname(request.getFirstname()) // Устанавливаем новому пользователю имя полученное из имени в параметрах регистрации
                .lastname(request.getLastname()) // Устанавливаем новому пользователю фамилию полученную из фамилию в параметрах регистрации
                .email(request.getEmail()) // Устанавливаем новому пользователю почту полученное из почты в параметрах регистрации
                .password(passwordEncoder.encode(request.getPassword())) // Полученный пароль из параметров регистрации, вначале кодируем с помощью системного метода спринг и затем устанавливаем его новому пользователю
                .role(Role.USER) // По умолчанию устанавливаем новому пользователю роль USER
                .build(); // собираем полученный объект

        userRepository.save(user); // сохраняем нового пользователя в базе данных
        var jwtToken = jwtService.generateToken(user); // создаем переменную которую инициализируем спомощью метода генерации токина
        return AuthenticationRespons.builder() // вызываем строителя для объекта токина аутентификации
                .token(jwtToken) // устанавливаем в качестве токина сгенерированый токин
                .build(); // собираем полученный объект
    }

    public AuthenticationRespons authenticate(AuthenticationRequest request) { // метод возвращает токин аутентификации с объектом в параметрах аутентификации
        authenticationManager.authenticate( // вызываем метод аутентификации у объекта менеджера аутентификации
                new UsernamePasswordAuthenticationToken( // создаем объект токена аутентификацуии по логину и паролю
                        request.getEmail(), // получаем логин из объекта в параметрах аутентификации
                        request.getPassword() // получаем пароль из объекта в параметрах аутентификации
                )
        ); // Если метод не найдет такие данные пользователя, то выбрасить исключение, а если надет то код отработает дальше
        var user = userRepository.findByEmail(request.getEmail()) // создаем переменную которую инициализируем, с помощью метода который находит в базе данных находя его по почте
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user); // создаем переменную которую инициализируем спомощью метода генерации токина
        return AuthenticationRespons.builder()  // вызываем строителя для объекта токина аутентификации
                .token(jwtToken) // устанавливаем в качестве токина сгенерированый токин
                .build(); // собираем полученный объект

    }
}
