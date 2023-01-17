package com.example.securitymaster.security.config;

import static com.example.securitymaster.security.SecurityRoles.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        var uds = new InMemoryUserDetailsManager();

        var john = User.withUsername("john")
                .password("12345")
                .roles(ROLES_ADMIN)
                .build();

        var emma = User.withUsername("emma")
                .password("12345")
                .roles(EMPLOYEES_ADMIN)
                .build();

        var william = User.withUsername("william")
                .password("12345")
                .roles(DEPARTMENTS_CREATE,DEPARTMENTS_PAG_VIEW,DEPARTMENTS_READ)
                .build();

        var lucas = User.withUsername("lucas")
                .password("12345")
                .roles(CUSTOMERS_READ,CUSTOMERS_PAG_VIEW)
                .build();

        var tom = User.withUsername("tom")
                .password("12345")
                .roles()
                .build();

        uds.createUser(john);
        uds.createUser(emma);
        uds.createUser(william);
        uds.createUser(lucas);
        uds.createUser(tom);

        return  uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Throwable{
        http.authorizeHttpRequests()
                .requestMatchers("/","/home","/bootstrap/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
