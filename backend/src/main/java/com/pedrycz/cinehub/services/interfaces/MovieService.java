package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import org.springframework.data.domain.Page;

public interface MovieService {

    MovieDTO getById(String id);
    Page<MovieDTO> getByTitleMatching(String title, GetParams getParams);
    Page<MovieDTO> getAll(GetParams params);
    Page<MovieDTO> getByDirector(String director, GetParams params);
    Page<MovieDTO> getByActor(String actor, GetParams params);
    Page<MovieDTO> getByGenre(String genre, GetParams params);
    Page<MovieDTO> getByRuntimeBetween(int min, int max, GetParams params);
    Page<MovieDTO> getShorts(GetParams params);
    Page<MovieDTO> getFullLength(GetParams params);
    MovieDTO add(AddMovieDTO movie);
    MovieDTO updateById(String id, AddMovieDTO movieDTO);

    void deleteById(String id);
}
