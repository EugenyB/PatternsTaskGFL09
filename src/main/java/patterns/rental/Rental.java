package patterns.rental;

import patterns.movie.Movie;

public record Rental(Movie movie, int daysRented) {
}
