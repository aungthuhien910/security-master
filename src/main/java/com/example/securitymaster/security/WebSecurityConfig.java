package com.example.securitymaster.security;

import static com.example.securitymaster.security.SecurityRoles.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration

public class WebSecurityConfig {

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Bean
    public UserDetailsService userDetailsService() {
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
                .roles(DEPARTMENTS_CREATE, DEPARTMENTS_PAG_VIEW, DEPARTMENTS_READ)
                .build();

        var lucas = User.withUsername("lucas")
                .password("12345")
                .roles(CUSTOMERS_READ, CUSTOMERS_PAG_VIEW)
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

        return uds;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Throwable {
        http.authorizeRequests()
                .expressionHandler(expressionHandler())
                .requestMatchers("/", "/home", "/bootstrap/**")
                .permitAll()
                .requestMatchers("/customer/**").hasRole(CUSTOMERS_PAG_VIEW)
                .requestMatchers("/employee/**").hasRole(EMPLOYEES_ADMIN)
                .requestMatchers("/department/departments","/department/depart-form").hasAnyRole(DEPARTMENTS_PAG_VIEW,DEPARTMENTS_READ,DEPARTMENTS_CREATE)
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler expressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);
        return handler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
