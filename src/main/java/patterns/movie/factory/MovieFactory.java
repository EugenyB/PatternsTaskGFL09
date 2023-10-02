package patterns.movie.factory;

import patterns.movie.Movie;

import java.util.List;

public interface MovieFactory {
    Movie createMovie(String title, String country, String shortDescription, String director, List<String> actors);
}
