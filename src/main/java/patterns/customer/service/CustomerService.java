package patterns.customer.service;

import patterns.customer.Customer;
import patterns.customer.factory.CustomerFactory;
import patterns.exception.MovieNotFoundException;
import patterns.rental.Rental;
import patterns.rental.controller.RentalController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final RentalController rentalController;
    private final CustomerFactory customerFactory;
    private final List<Customer> customers;

    public CustomerService(RentalController rentalController) {
        this.rentalController = rentalController;
        customers = new ArrayList<>();
        customerFactory = new CustomerFactory();
        loadCustomers();
    }

    private void loadCustomers() {
        List<Rental> customerRentals = rentalController.getRentals();
        Customer defaultCustomer = new Customer("John Doe", customerRentals);
        customers.add(defaultCustomer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }

    public Customer addCustomer(String name) {
        return addCustomer(name, new ArrayList<>());
    }

    public Customer addCustomer(String name, List<Rental> rentals) {
        Customer newCustomer = customerFactory.build(name, rentals);
        customers.add(newCustomer);
        return newCustomer;
    }

    public void addRental(Customer customer, String movie, int daysInRent) {
        try {
            Rental rental = rentalController.addRental(movie, daysInRent);
            customer.addMovieToRentalList(rental);
        } catch (MovieNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listMovies() {
        rentalController.listMovies();
    }
}
