package com.wajdigabsi.book.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExcpetionResponse {

    private Integer businesErrorCode ;
    private String businesErrorDescription;
    private String error ;
    private Set<String> validationError;
    private Map<String, String> errors;

}
