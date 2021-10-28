package project.tuthree.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.ParseException;

public interface ExceptionSupplier<T> {
    T get() throws ParseException, JsonProcessingException;
}
