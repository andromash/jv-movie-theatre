package com.hibernate.cinema;

import com.hibernate.cinema.lib.Injector;
import com.hibernate.cinema.model.Movie;
import com.hibernate.cinema.service.MovieService;

public class Main {
      private static Injector injector = Injector.getInstance("com.hibernate.cinema");
    
      public static void main(String[] args) {
          Movie movie = new Movie();
          movie.setTitle("Fast and Furious");
          MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
          movieService.add(movie);
    
          movieService.getAll().forEach(System.out::println);
      }
  }