package project.tuthree.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.text.ParseException;

public interface ExceptionSupplier<T> {

//    T get() throws ParseException, JsonProcessingException;
    T get() throws Exception;

}
