package com.project.literatureclub.dtos;


import com.project.literatureclub.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String mainGenre;
    private Date dateOfBirth;
    private String description;
    private Role role;
}
