package patterns.customer.controller;

import patterns.Util;
import patterns.rental.controller.RentalController;
import patterns.customer.Customer;
import patterns.customer.service.CustomerService;
import patterns.exception.CustomerNotFoundException;
import patterns.strategy.ConsoleOutputStrategy;
import patterns.strategy.HTMLOutputStrategy;

import java.util.Optional;
import java.util.Scanner;

import static patterns.Util.*;

public class CustomerController {

    private final Scanner scanner = Util.getScanner();
    private final CustomerService customerService;

    public CustomerController(RentalController rentalController) {
        customerService = new CustomerService(rentalController);
    }


    public void customerActions() {
        while (true) {
            System.out.println("""
                    What you like to do? (Customers)
                     1. List all customers
                     2. Retrieve customer's information
                     3. Create new customer
                     4. Rent a movie
                     0. Back
                    """);
            switch (userChoice()) {
                case 1:
                    listCustomers();
                    break;
                case 2:
                    listDetailedInformationAboutCustomer();
                    break;
                case 3:
                    addCustomer();
                    break;
                case 4:
                    rentAMovie();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listCustomers() {
        System.out.println("""
                =========
                Customers
                =========""");
        customerService.getCustomers().stream().map(Customer::getName).forEach(System.out::println);
        System.out.println();
    }

    private void listDetailedInformationAboutCustomer() {
        Customer customer = null;
        try {
            customer = getCustomer();
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (isCustomer(customer)) {
            System.out.println(customer);
            System.out.println("    Would you like to save information to the file? ");
            printYesOrNo();
            if (userChoice() == 1) {
                chooseOutputFormat(customer);
                saveToFile(customer);
            }
        }
    }

    private Customer addCustomer() {
        System.out.print("  Write your name: ");
        String name = scanner.nextLine();
        Customer customer = customerService.addCustomer(name);
        System.out.println("    Hello, " + customer.getName());
        return customer;
    }

    private Customer getCustomer() throws CustomerNotFoundException {
        Customer customer;
        System.out.print("  What is the name of the customer: ");
        String name = scanner.nextLine();
        Optional<Customer> optionalCustomer = customerService.getCustomerByName(name);
        if (optionalCustomer.isEmpty()) {
            System.out.println("    Customer " + name + " is not registered.\n Do you want to create a new customer?");
            printYesOrNo();
            if (userChoice() == 1) {
                customer = addCustomer();
            } else {
                throw new CustomerNotFoundException("You don`t have customer account. I should add new customer.");
            }
        } else {
            customer = optionalCustomer.get();
        }
        return customer;
    }

    private void rentAMovie() {
        System.out.println("    Do you want to rent a movie?");
        printYesOrNo();

        if (userChoice() == 1) {
            Customer customer = null;
            try {
                customer = getCustomer();
            } catch (CustomerNotFoundException e) {
                System.out.println(e.getMessage());
            }
            if (isCustomer(customer)) {
                chooseMovie(customer);
                System.out.println("    Do you want to see your information?");
                printYesOrNo();

                if (userChoice() == 1) {
                    chooseOutputFormat(customer);
                }
                System.out.println(customer.statement());
            }
        }
    }

    private void chooseOutputFormat(Customer customer) {
        System.out.println("    Do you want it in HTML-format or in Console?");
        System.out.println("     1. HTML\n2. Console");
        if (userChoice() == 1) {
            customer.setOutputStrategy(new HTMLOutputStrategy());
        } else {
            customer.setOutputStrategy(new ConsoleOutputStrategy());
        }
    }

    private void chooseMovie(Customer customer) {
        customerService.listMovies();
        System.out.println("    What movie do you want to rent: [movie title]");
        String movieTitle = scanner.nextLine();

        System.out.println("    What is the duration of the rent? ");
        int duration = Integer.parseInt(scanner.nextLine().substring(0, 1));

        customerService.addRental(customer, movieTitle, duration);

        System.out.println("    You successfully rented a movie " + movieTitle);
    }

    private boolean isCustomer(Customer customer) {
        return customer != null;
    }
}
