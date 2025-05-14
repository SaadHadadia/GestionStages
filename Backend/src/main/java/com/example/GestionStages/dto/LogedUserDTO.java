package com.example.GestionStages.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogedUserDTO {
    private String username;
    private String type;
    private String token;
}
