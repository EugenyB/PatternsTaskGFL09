package patterns.customer;

import patterns.rental.Rental;
import patterns.strategy.ConsoleOutputStrategy;
import patterns.strategy.OutputStrategy;

import java.util.List;

import static patterns.movie.Movie.MovieType.NEW_RELEASE;

public class Customer {
    private final String name;
    private final List<Rental> rentals;

    private OutputStrategy outputStrategy = new ConsoleOutputStrategy();

    public Customer(String name, List<Rental> rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    public OutputStrategy getOutputStrategy() {
        return outputStrategy;
    }

    public void setOutputStrategy(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", rentals=" + rentals +
                ", outputStrategy=" + outputStrategy +
                '}';
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for ").append(getName()).append("\n");

        for (Rental each : rentals) {
            double thisAmount = calculateRentalAmount(each);
            frequentRenterPoints += calculateFrequentRenterPoints(each);
            result.append(formatRentalLine(each, thisAmount));
            totalAmount += thisAmount;
        }

        result.append("Amount owed is ").append(String.format("%.2f", totalAmount)).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");

        return outputStrategy.generateOutput(result.toString());
    }

    private double calculateRentalAmount(Rental rental) {
        double thisAmount = 0;
        switch (rental.movie().getPriceCode()) {
            case REGULAR:
                thisAmount += 2;
                if (rental.daysRented() > 2)
                    thisAmount += (rental.daysRented() - 2) * 1.5;
                break;
            case NEW_RELEASE:
                thisAmount += rental.daysRented() * 3;
                break;
            case CHILDRENS:
                thisAmount += 1.5;
                if (rental.daysRented() > 3)
                    thisAmount += (rental.daysRented() - 3) * 1.5;
                break;
            case DRAMA:
                thisAmount += 1.2;
                if (rental.daysRented() > 1)
                    thisAmount += (rental.daysRented() - 1) * 2;
                break;
            case COMEDY:
                if (rental.daysRented() > 5)
                    thisAmount += rental.daysRented() * 0.8;
                break;
            case THRILLER:
                if (rental.daysRented() > 4)
                    thisAmount += (rental.daysRented() - 2) * 2;
                break;
        }
        return thisAmount;
    }

    private int calculateFrequentRenterPoints(Rental rental) {
        int points = 1;
        if ((rental.movie().getPriceCode() == NEW_RELEASE) && rental.daysRented() > 1)
            points++;
        return points;
    }

    private String formatRentalLine(Rental rental, double amount) {
        return "\t" + rental.movie().getTitle() + "\t" + String.format("%.2f", amount) + "\n";
    }

    public void addMovieToRentalList(Rental rental){
        this.rentals.add(rental);
    }

    public static class Builder {
        private String name;
        private List<Rental> rentals;

        public Builder(String name, List<Rental> rentals) {
            this.name = name;
            this.rentals = rentals;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRentals(List<Rental> rentals) {
            this.rentals = rentals;
            return this;
        }

        public Customer build() {
            if (name == null || rentals == null) {
                throw new IllegalStateException("Name and rentals must be set.");
            }

            return new Customer(name, rentals);
        }
    }

}