package ua.app.springjdbc.exception;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }
}
