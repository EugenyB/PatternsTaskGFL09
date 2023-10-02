package patterns.movie.controller;

import patterns.Util;
import patterns.exception.MovieNotFoundException;
import patterns.movie.Movie;
import patterns.movie.service.MovieService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static patterns.Util.userChoice;


public class MovieController {
    private final Scanner scanner = Util.getScanner();
    private final MovieService movieService;

    public MovieController() {
        movieService = new MovieService();
    }

    public void movieActions() {
        while (true) {
            System.out.println("""
                    What you like to do? (Movies)
                    1. List all movies
                    2. Add movie to the collection
                    3. List information about movie
                    4. List movies by specific genre
                    0. Back
                    """);
            switch (userChoice()) {
                case 1:
                    listMovies();
                    break;
                case 2:
                    addMovieToCollection();
                    break;
                case 3:
                    listDetailedInformationAboutMovie();
                    break;
                case 4:
                    listByGenre();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void addMovieToCollection() {
        System.out.println("    What is the title?");
        String title = scanner.nextLine();

        String movieType;
        boolean isGenre;

        do {
            System.out.println("    What is movie genre? (REGULAR, NEW_RELEASE, CHILDRENS, DRAMA, COMEDY, THRILLER) [REGULAR]");
            movieType = scanner.nextLine().toUpperCase();
            isGenre = movieService.isGenre(movieType);
            if (!isGenre) {
                System.out.println("Wrong genre. Choose genre from the hint.");
            }
        } while (!isGenre);

        System.out.println("    What is the origin country?");
        String country = scanner.nextLine();
        System.out.println("    Write a short description?");
        String description = scanner.nextLine();
        System.out.println("    Who is director?");
        String director = scanner.nextLine();
        System.out.println("    List starring actors (followed by comma)");
        List<String> actors = Arrays.stream(scanner.nextLine().split(",")).toList();

        Movie movie = movieService.addMovie(title, movieType, country, description, director, actors);
        System.out.println("    " + movie.getTitle() + " was saved.");

    }


    public void listMovies() {
        System.out.println("""
                ================
                Movie Collection
                ================""");
        movieService.getMovies().stream().map(Movie::getTitle).forEach(System.out::println);
        System.out.println();
    }


    private void listDetailedInformationAboutMovie() {
        System.out.println("    What is the title of the movie? ");
        String title = scanner.nextLine();
        try {
            System.out.println(getMovieByTitle(title));
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listByGenre() {
        System.out.println("    What is movie genre? (REGULAR, NEW_RELEASE, CHILDRENS, DRAMA, COMEDY, THRILLER) [REGULAR]");
        String genre = scanner.nextLine().toUpperCase();
        if (movieService.isGenre(genre)) {
            List<Movie> movieByGenre = movieService.getMovieByGenre(Movie.MovieType.valueOf(genre));
            movieByGenre.forEach(System.out::println);
        } else {
            System.out.println("This is not valid movie genre.");
        }
    }

    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
        return movieService.getMovieByTitle(title);
    }

}
