package com.example.springtest2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "5970337336763979244226452948404D6251655468576D5A7134743777217A25"; //секретный ключ

    public String extractUsername(String token) { //метод извлекает из токена jwt логин пользовтеля
        return extractClaims(token, Claims::getSubject); //возвращаем результат метода, в который передаем токен и тему утверждения, которой является логин
    }

    // универсальный метод для извлечения утверждения
    public <T> T extractClaims (String token, Function<Claims, T> claimsResolver){ //(токен, Функция из пакета java.util
        final  Claims claims = extractAllClaims(token); // объект утверждений
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){ // генерация токина (данные пользователя)
        return generateToken(new HashMap<>(), userDetails); // используем созданый метод, в который передаем новую пустую хешМапу и полученые данные пользователя
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){ // генерация токина (Мар дополнительного утверждения, данные пользователя)
        return Jwts //Взят из пакета io.jsonwebtoken
                .builder() // строитель
                .setClaims(extraClaims) // устанавливаем утверждение
                .setSubject(userDetails.getUsername()) //установка темы
                .setIssuedAt(new Date(System.currentTimeMillis())) // устанавливаем дату когда было создано это объявление
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // устанавливаем дату истечения срока действия
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // определяем какой ключ будем использовать для подписи (получаем с помощью созданого метода ключ, и указываем какой алгоритм подписи хотим использовать)
                .compact(); // метод который сгенерирует и вернет токен

    }

    public boolean isTokenValid(String token, UserDetails userDetails){ // метод по проверки токена на допустимые значения
        final String username = extractUsername(token); // извлекаем логин пользователя из токена с помощью созданного метода
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // сравниваем полученый логин из токина с логином из данных пользователя
        // и срок действия токина не истек
    }

    private boolean isTokenExpired(String token) { // метод проверки истек ли срок действия токина
        return extractExpiration(token).before(new Date()); // извлекаем срок действия токина
    }

    private Date extractExpiration(String token) { // Срок действия токена
        return extractClaims(token, Claims::getExpiration); //возвращаем результат метода, в который передаем токен и срок действия
    }

    private Claims extractAllClaims (String token){ // Извлекает все утверждения, возвращая ответ с типом класса из пакета io.jsonwebtoken
        return Jwts //Взят из пакета io.jsonwebtoken
                .parserBuilder() // конструктор парс
                .setSigningKey(getSignInKey())// устанавливаем ключ подписи (метод ключа входа в систему)
                .build() // постороить
                .parseClaimsJws(token) // передает утверждение Jws (токен)
                .getBody(); //внутри тела гет получаем все утверждения
    }

    private Key getSignInKey() { // метод который возвращает ключ для входа в систему
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // декодер . базовое точечное декодирование 64 . расшифровать (используя секретный ключ)
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
