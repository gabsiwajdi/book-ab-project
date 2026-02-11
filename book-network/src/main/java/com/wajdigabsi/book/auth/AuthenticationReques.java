package com.wajdigabsi.book.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AuthenticationReques {

    @NotEmpty(message = "email name is mandatory")
    @NotBlank(message = "eamil name is mandatory")
    @Email(message = "email is not formatetd")
    private String email ;
    @NotEmpty(message = "password name is mandatory")
    @NotBlank(message = "password name is mandatory")
    @Size(min = 8, message = "¨Password should be 8 characters lon minimum")
    private String password ;



}
