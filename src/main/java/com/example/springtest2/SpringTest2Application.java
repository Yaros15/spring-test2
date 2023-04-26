package com.example.springtest2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTest2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringTest2Application.class, args);
    }


/*
    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(new Role("USER"));
            userService.saveRole(new Role("ADMIN"));

            userService.saveUser(new User("администратор", "admin", "admin", new ArrayList<>()));
            userService.saveUser(new User("пользователь", "user", "user", new ArrayList<>()));

            userService.addRoleToUser("admin", "ADMIN");
            userService.addRoleToUser("admin", "USER");
            userService.addRoleToUser("user", "USER");
        };
    }
*/

}
