package patterns.example;

import patterns.Util;
import patterns.customer.controller.CustomerController;
import patterns.movie.controller.MovieController;
import patterns.rental.controller.RentalController;

import static patterns.Util.userChoice;


public class Main {

//    public static void main2(String[] args) {
//        List<Rental> rentals = List.of(new Rental(new Movie("Rambo", REGULAR), 1),
//                new Rental(new Movie("Lord of the Rings", NEW_RELEASE), 4),
//                new Rental(new Movie("Harry Potter", CHILDRENS), 5));
//
//        Customer customer = new Customer("John Doe", rentals);
//        String statement = customer.statement()
//
//        System.out.println(statement);
//    }

    private static final MovieController movieController;
    private static final RentalController rentalController;
    private static final CustomerController customerController;

    static {
        movieController = new MovieController();
        rentalController = new RentalController(movieController);
        customerController = new CustomerController(rentalController);
    }

    public static void main(String[] args) {
        enhancedMenu();
    }


    static void enhancedMenu() {
        while (true) {
            System.out.println("""
                    Rental service:
                    1. Movies
                    2. Customers
                    0. Exit""");
            int i = userChoice();
            switch (i) {
                case 1:
                    movieController.movieActions();
                    break;
                case 2:
                    customerController.customerActions();
                    break;
                case 0:
                    Util.getScanner().close();
                    return;
            }
        }
    }


}