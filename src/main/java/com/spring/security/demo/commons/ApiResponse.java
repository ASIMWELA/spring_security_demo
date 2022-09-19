package com.spring.security.demo.commons;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter@Setter
public class ApiResponse {
    private boolean sucess;
    private String message;
}
