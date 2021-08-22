package obss.project.finalproject.exception;

public class BaseException extends RuntimeException {

    private String message;
    private Throwable cause;

    public BaseException(String message, Throwable path) {
        this.message = message;
        this.cause = path;
    }

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(Throwable cause) {
        this.cause = cause;
    }
}
