package patterns.rental.controller;

import patterns.exception.MovieNotFoundException;
import patterns.movie.controller.MovieController;
import patterns.rental.Rental;
import patterns.rental.service.RentalService;

import java.util.List;

public class RentalController {

    private final RentalService rentalService;
    private final MovieController movieController;


    public RentalController(MovieController movieController) {
        this.movieController = movieController;
        rentalService = new RentalService(movieController);
    }

    public void listRentals() {
        rentalService.getRentals().forEach(System.out::println);
    }

    public List<Rental> getRentals() {
        return rentalService.getRentals();
    }


    public Rental addRental(String name, int daysInRent) throws MovieNotFoundException {
        return rentalService.addRental(name, daysInRent);
    }

    public void listMovies() {
        movieController.listMovies();

    }
}
