package az.edu.turing.msauth.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String invalidRefreshToken) {
        super(invalidRefreshToken);
    }
}
