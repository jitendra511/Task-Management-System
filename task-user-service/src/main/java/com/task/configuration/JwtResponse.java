package com.task.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String jwt;
    private String message;
    private boolean status;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDetails {
        private String email;
        private String password;
    }
}
