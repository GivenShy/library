package com.project.literatureclub.dtos;

import com.project.literatureclub.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchResponseDTO {

    @Getter
    @Setter
    private List<UserResponseDTO> userList;

    @Getter
    @Setter
    private List<ArticleResponseDTO> articleList;
}
