package com.wajdigabsi.book.handler;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
public enum BusinessErrorCodes {

    NO_CODE (0,NOT_IMPLEMENTED,"No code"),

    INCORRECT_CURRENT_PASSWORD (300,BAD_REQUEST,"Incorrect current password"),

    NEW_PASSWORD_DOES_NOT_MATCH (301,BAD_REQUEST,"New password Does Not Match "),

    ACCOUNT_LOCKED (302,FORBIDDEN,"User Account is locked "),

    ACCOUNT_DISABLED (303,FORBIDDEN,"User Account is Disabled "),

    BAD_CREDENTIALS (304, FORBIDDEN, "Login and / or password are incorrect"),

    ;

    @Getter
    private final int code ;
    @Getter
    private final String description ;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code,HttpStatus httpStatus ,String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
