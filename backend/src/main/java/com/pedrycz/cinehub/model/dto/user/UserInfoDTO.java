package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Review;
import lombok.Data;

import java.util.Set;

@Data
public class UserInfoDTO {

    private String nickname;
    private Set<MovieDTO> watchLater;
    private Set<Review> myReviews;
}
