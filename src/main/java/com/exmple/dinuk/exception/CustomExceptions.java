package com.exmple.dinuk.exception;

public class CustomExceptions {

    public static class UserExistsException extends RuntimeException {
        public UserExistsException(String message) {
            super(message);
        }
    }

    public static class PasswordMismatchException extends RuntimeException {
        public PasswordMismatchException(String message) {
            super(message);
        }
    }

    public static class EmailNotSentException extends RuntimeException {
        public EmailNotSentException(String message) {
            super(message);
        }
    }

    public static class UserNotVerifiedException extends RuntimeException {
        public UserNotVerifiedException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    public static class UserDoesNotExistException extends RuntimeException {
        public UserDoesNotExistException(String message) {
            super(message);
        }
    }

    public static class InvalidOTPException extends RuntimeException {
        public InvalidOTPException(String message) {
            super(message);
        }
    }
    public static class InvalidJwtTokenException extends RuntimeException {
        public InvalidJwtTokenException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class NoPostsFoundException extends RuntimeException {
        public NoPostsFoundException(String message) {
            super(message);
        }
    }
}