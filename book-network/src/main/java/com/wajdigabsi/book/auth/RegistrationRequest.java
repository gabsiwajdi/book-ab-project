package com.wajdigabsi.book.auth;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.IMessage;


@Getter
@Setter
@Builder
public class RegistrationRequest {


    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name is mandatory")
    private String firstname ;
    @NotEmpty(message = "last name is mandatory")
    @NotBlank(message = "last name is mandatory")
    private String lastname ;
    @NotEmpty(message = "email name is mandatory")
    @NotBlank(message = "eamil name is mandatory")
    @Email(message = "email is not formatetd")
    private String email ;
    @NotEmpty(message = "password name is mandatory")
    @NotBlank(message = "password name is mandatory")
    @Size(min = 8, message = "¨Password should be 8 characters lon minimum")
    private String password ;

}
