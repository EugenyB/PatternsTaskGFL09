package patterns.movie;

import java.util.List;

public class Movie {
    private final String title;
    private final MovieType priceCode;
    private final String country;
    private final String shortDescription;
    private final String director;
    private final List<String> actors;

    private Movie(Builder builder) {
        this.title = builder.title;
        this.priceCode = builder.priceCode;
        this.country = builder.country;
        this.shortDescription = builder.shortDescription;
        this.director = builder.director;
        this.actors = builder.actors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", priceCode=" + priceCode +
                ", country='" + country + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", director='" + director + '\'' +
                ", actors=" + actors +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public MovieType getPriceCode() {
        return priceCode;
    }

    public String getCountry() {
        return country;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getActors() {
        return actors;
    }

    public enum MovieType {
        REGULAR, NEW_RELEASE, CHILDRENS, DRAMA, COMEDY, THRILLER
    }


    public static class Builder {

        private String title;
        private MovieType priceCode;

        private String country;
        private String shortDescription;
        private String director;
        private List<String> actors;

        public Movie build() {
            return new Movie(this);
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder priceCode(MovieType priceCode) {
            this.priceCode = priceCode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder shortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder director(String director) {
            this.director = director;
            return this;
        }

        public Builder actors(List<String> actors) {
            this.actors = actors;
            return this;
        }
    }
}