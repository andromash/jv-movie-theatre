package com.hibernate.cinema.service.mapper;

import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.model.dto.MovieRequestDto;
import com.hibernate.cinema.model.dto.MovieResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public Movie mapDtoToMovie(MovieRequestDto movieRequestDto) {
        Movie movie = new Movie();
        movie.setDescription(movieRequestDto.getDescription());
        movie.setTitle(movieRequestDto.getTitle());
        return movie;
    }

    public MovieResponseDto mapMovieToDto(Movie movie) {
        MovieResponseDto movieResponseDto = new MovieResponseDto();
        movieResponseDto.setDescription(movie.getDescription());
        movieResponseDto.setMovieId(movie.getId());
        movieResponseDto.setTitle(movie.getTitle());
        return movieResponseDto;
    }
}
