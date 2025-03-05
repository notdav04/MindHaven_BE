package com.example.MindHaven_BE.payload.response;

import com.example.MindHaven_BE.model.Diario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private String token;

}
