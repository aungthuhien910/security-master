package com.example.securitymaster.ds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First Name cannot be empty.")
    @Pattern(regexp = "[A-Za-z-]*",message = "First Name cannot contain illegal characters.")
    private String firstName;

    @NotBlank(message = "Last Name cannot be empty.")
    @Pattern(regexp = "[A-Za-z-]*",message = "Last Name cannot contain illegal characters.")
    private String lastName;

    @NotBlank(message = "Phone Number cannot be empty.")
    @Pattern(regexp = "[0-9\\-+]*",message = "Phone Number cannot contain illegal characters.")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be empty.")
    @Pattern(regexp = "[\\w .\\-/,]*",message = "Address cannot contain illegal characters.")
    private String address;

    @NotBlank(message = "Cubicle Number cannot be empty.")
    @Pattern(regexp = "[A-Za-z0-9\\-]*",message = "Cubicle Number cannot contain illegal characters.")
    private String cubicleNo;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String phoneNumber, String address, String cubicleNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cubicleNo = cubicleNo;
    }
}
