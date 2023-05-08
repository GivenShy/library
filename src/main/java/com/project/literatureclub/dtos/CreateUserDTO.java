package com.project.literatureclub.dtos;

import com.project.literatureclub.entities.Role;
import lombok.Getter;
import lombok.Setter;

public class CreateUserDTO {
    @Getter
    @Setter
    public long id;

    @Getter
    @Setter
    public String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private Role role;

    @Getter
    @Setter
    private String description;
}
