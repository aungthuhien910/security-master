package com.example.securitymaster.security.annotation.customer;
import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.securitymaster.security.SecurityRoles.*;

@Retention(RetentionPolicy.RUNTIME)
@Secured(ROLES_PREFIX+CUSTOMERS_READ)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface CustomersRead {
}
