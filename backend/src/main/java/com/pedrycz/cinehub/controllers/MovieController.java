package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.MovieQueryParams;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.UUID;

@RestController
@RequestMapping(value = "/movies")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Requests for getting single movies, or pages of movies, updating existing and adding new movies.")
public class MovieController {

    private static final int SHORTS_MAX_RUNTIME = 60;

    private final MovieService movieService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movie", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested movie found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class)))
    })
    @Operation(summary = "Get movie by ID", description = "Get movie of requested ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable UUID id) {
        return new ResponseEntity<>(movieService.getById(id), HttpStatus.OK);
    }

    @GetMapping({"/all/{page}", "/all/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies page", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page for all movies found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get one of pages of all movies ordered",
            description = "Get requested page from all movies ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getAllMovies(@PathVariable int page,
                                                       @PathVariable(required = false) String orderBy,
                                                       @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.empty(), new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/title/{title}/{page}", "/title/{title}/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies page for this title", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page for this title found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page for title of movies ordered",
            description = "Get page of movies with title containing requested string ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getMoviesByTitle(@PathVariable String title,
                                                           @PathVariable int page,
                                                           @PathVariable(required = false) String orderBy,
                                                           @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().title(title).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/director/{director}/{page}", "/director/{director}/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies page for this director", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page for this director found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page for director of movies ordered",
            description = "Get page of movies with director name containing requested string ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getMoviesByDirector(@PathVariable String director,
                                                              @PathVariable int page,
                                                              @PathVariable(required = false) String orderBy,
                                                              @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().director(director).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/actor/{actor}/{page}", "/actor/{actor}/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies page for this actor", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page for this actor found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page for actor in movies ordered",
            description = "Get page of movies with actor name containing requested string ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getMoviesByActor(@PathVariable String actor,
                                                           @PathVariable int page,
                                                           @PathVariable(required = false) String orderBy,
                                                           @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().actor(actor).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/genre/{genre}/{page}", "/genre/{genre}/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies page for this genre", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page for this genre found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page for genre of movies ordered",
            description = "Get page of movies with genre name containing requested string ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getMoviesByGenre(@PathVariable String genre,
                                                           @PathVariable int page,
                                                           @PathVariable(required = false) String orderBy,
                                                           @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().genre(genre).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/runtime/shorts/{page}", "/runtime/shorts/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested short movies page", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested short movies page found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page of short movies ordered", description = "Get page of short movies ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getShorts(@PathVariable int page,
                                                    @PathVariable(required = false) String orderBy,
                                                    @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().maxRuntime(SHORTS_MAX_RUNTIME).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/runtime/full/{page}", "/runtime/full/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested full length movies page", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page of full length found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page of full lenght movies ordered",
            description = "Get page of full length movies ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getFullLength(@PathVariable int page,
                                                        @PathVariable(required = false) String orderBy,
                                                        @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().minRuntime(SHORTS_MAX_RUNTIME).build(), new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @GetMapping({"/runtime/{min}/{max}/{page}", "/runtime/{min}/{max}/{page}/{orderBy}/{isAscending}"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Couldn't find requested movies with runtime in requested range", content = @Content),
            @ApiResponse(responseCode = "200", description = "Requested page of movies with runtime in requested range found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    })
    @Operation(summary = "Get page of movies with runtime in range ordered",
            description = "Get page of movies with runtime between specified range ordered by RATING, RELEASEYEAR or RUNTIME")
    public ResponseEntity<Page<MovieDTO>> getMoviesByRuntime(@PathVariable int min, @PathVariable int max,
                                                             @PathVariable int page,
                                                             @PathVariable(required = false) String orderBy,
                                                             @PathVariable(required = false) boolean isAscending) {
        return new ResponseEntity<>(movieService.getBy(MovieQueryParams.builder().minRuntime(min).maxRuntime(max).build(),
                new SortParams(page, orderBy, isAscending)), HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "You're not logged in user", content = @Content),
            @ApiResponse(responseCode = "201", description = "New movie added to database",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class)))
    })
    @Operation(summary = "Add new movie", description = "Add new movie with poster to database")
    public ResponseEntity<MovieDTO> addMovie(@Valid @ParameterObject @ModelAttribute AddMovieDTO movie) {
        MovieDTO movieDTO = movieService.add(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "You're not logged in user with ADMIN role", content = @Content),
            @ApiResponse(responseCode = "204", description = "Movie deleted", content = @Content)
    })
    @Operation(summary = "Delete movie", description = "Delete movie")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable UUID id) {
        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "You're not logged in user ", content = @Content),
            @ApiResponse(responseCode = "204", description = "Movie updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDTO.class)))
    })
    @Operation(summary = "Update movie", description = "Update movie data")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable UUID id, @Valid @ParameterObject @ModelAttribute AddMovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.updateById(id, movieDTO), HttpStatus.OK);
    }
}
