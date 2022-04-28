package farsharing.server.exception;

public class RequestNotValidException extends RuntimeException {

    public RequestNotValidException() {
    }

    public RequestNotValidException(String message) {
        super(message);
    }
}
