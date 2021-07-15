package edu.javacourse.studentorder.exception;

public class CityRegisterException extends Exception{
    public CityRegisterException() {
        super();
    }

    public CityRegisterException(String message) {
        super(message);
    }

    public CityRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
