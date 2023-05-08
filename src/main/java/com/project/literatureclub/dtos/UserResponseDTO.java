package com.project.literatureclub.dtos;

import lombok.*;

import javax.swing.plaf.PanelUI;
import java.sql.Date;

@Builder
public class UserResponseDTO {



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
    private String description;

    @Getter
    @Setter
    private long posts;

    @Getter
    @Setter
    private Date dateOfBirth;

    @Getter
    @Setter
    private String MainGenre;


    @Getter
    @Setter
    private boolean isLiked;
}
