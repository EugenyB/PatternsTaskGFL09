package patterns.customer.factory;

import patterns.customer.Customer;
import patterns.rental.Rental;

import java.util.List;

public class CustomerFactory {
    public Customer build(String name, List<Rental> rentals) {
        return new Customer.Builder(name, rentals)
                .build();
    }
}
