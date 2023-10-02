package patterns.exception;

public class MovieNotFoundException extends Throwable {


    public MovieNotFoundException(String message) {
        super(message);
    }
}
