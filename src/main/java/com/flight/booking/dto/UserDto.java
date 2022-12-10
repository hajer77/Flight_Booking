package com.flight.booking.dto;


import lombok.Data;

@Data
public class UserDto {
    private Long userId ;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phoneNumber;
}
