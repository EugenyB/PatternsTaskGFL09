package patterns.movie.service;

import patterns.exception.MovieNotFoundException;
import patterns.movie.Movie;
import patterns.movie.factory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieService {
    private final List<Movie> movies;
    private MovieFactory movieFactory;

    public MovieService() {
        movies = new ArrayList<>();
        movieFactory = new RegularMovieFactory();
        loadMovies();
    }

    private void loadMovies() {
        movieFactory = new ComedyMovieFactory();

        Movie comedy = movieFactory.createMovie("Home Alone", "USA", "Xmas adventure of the kid.",
                "Chris Columbus", List.of("Macaulay Culkin", "Joe Pesci", "Daniel Stern"));
        movies.add(comedy);

        movieFactory = new ChildrenMovieFactory();
        Movie children = movieFactory.createMovie("Alice in Wonderland", "USA", "Adventure Fantasy Film", "Tim Burton",
                List.of("Johnny Depp", "Anne Hathaway", "Hellena Bonem Carter"));
        movies.add(children);

        movieFactory = new DramaMovieFactory();
        Movie drama = movieFactory.createMovie("Babylon", "USA", "Some drama film", "Damien Chazelle",
                List.of("Brad Pitt", "Margot Robbie", "Diego Calva"));
        movies.add(drama);

        movieFactory = new NewReleaseMovieFactory();
        Movie newRelease = movieFactory.createMovie("Barbie", "USA", "A story of the world-famous doll", "Greta Gerwig",
                List.of("Margot Robbie", "Ryan Gosling", "America Ferrera"));
        movies.add(newRelease);

        movieFactory = new RegularMovieFactory();
        Movie regular = movieFactory.createMovie("Titanic", "Canada", "A wreckful story of the 20th century", "James Cameron",
                List.of("Leonardo DiCaprio", "Kate Winslet", "Billy Zane"));
        movies.add(regular);

        movieFactory = new ThrillerMovieFactory();
        Movie thriller = movieFactory.createMovie("Expend4bles", "Mexico",
                "Armed with every weapon they can get their hands on, the Expendables are the world's last " +
                        "line of defense and the team that gets called when all other options are off the table.",
                "Scott Waught", List.of("Jason Statham", "50 Cent", "Megan Fox"));
        movies.add(thriller);

        Movie shrek = new ChildrenMovieFactory().createMovie("Shrek", "Australia",
                "Onion-like ogre and his donkey", "Andrew Adamson",
                List.of("Mike Myers", "Eddie Murphy", "Camerom Diaz"));
        movies.add(shrek);

        Movie hp = new RegularMovieFactory().createMovie("Harry Potter", "GB",
                "Wizard boy in the hardlife story", "Chris Collumbus",
                List.of("Daniel Radcliff", "Rupert Green", "Emma Watson"));
        movies.add(hp);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movies.stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst();

        return optionalMovie.orElseThrow(() -> new MovieNotFoundException("No movie found with title " + title));
    }


    public List<Movie> getMovieByGenre(Movie.MovieType type) {
        return movies.stream()
                .filter(movie -> movie.getPriceCode() == type)
                .collect(Collectors.toList());
    }

    public Movie addMovie(String title, String genre, String country, String description, String director, List<String> actors) {

        Movie.MovieType movieType = Movie.MovieType.valueOf(genre);
        switch (movieType) {
            case COMEDY -> movieFactory = new ComedyMovieFactory();
            case DRAMA -> movieFactory = new DramaMovieFactory();
            case THRILLER -> movieFactory = new ThrillerMovieFactory();
            case CHILDRENS -> movieFactory = new ChildrenMovieFactory();
            case NEW_RELEASE -> movieFactory = new NewReleaseMovieFactory();
            default -> movieFactory = new RegularMovieFactory();
        }
        Movie movie = movieFactory.createMovie(title, country, description, director, actors);
        movies.add(movie);
        return movie;
    }


    public boolean isGenre(String genre) {
        for (Movie.MovieType enumConstant : Movie.MovieType.class.getEnumConstants()) {
            if (enumConstant.name().equals(genre)) return true;
        }
        return false;
    }
}
