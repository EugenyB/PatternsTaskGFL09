package patterns.rental.service;

import patterns.exception.MovieNotFoundException;
import patterns.movie.Movie;
import patterns.movie.controller.MovieController;
import patterns.rental.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private final MovieController movieController;

    private final List<Rental> rentals;

    public RentalService(MovieController movieController) {
        this.movieController = movieController;
        rentals = new ArrayList<>();
        loadRentals();
    }

    private void loadRentals() {
        Movie homeAlone = null;
        Movie harryPotter = null;
        try {
            harryPotter = movieController.getMovieByTitle("Harry Potter");
            homeAlone = movieController.getMovieByTitle("Home Alone");
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (homeAlone != null && harryPotter != null) {
            Rental r1 = new Rental(homeAlone, 7);

            rentals.add(r1);

            Rental r2 = new Rental(harryPotter, 2);
            rentals.add(r2);
        }
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public Rental addRental(String movieName, int daysOfRent) throws MovieNotFoundException {
        Movie movieByTitle = movieController.getMovieByTitle(movieName);
        Rental rental = new Rental(movieByTitle, daysOfRent);
        rentals.add(rental);
        return rental;
    }
}
