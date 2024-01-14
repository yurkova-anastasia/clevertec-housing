package ru.clevertec.house.exception;

public class RepositoryException extends RuntimeException {
    public RepositoryException() {
        super();
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(String message) {
        super(message);
    }
}