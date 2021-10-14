package project.tuthree.exception;

import java.text.ParseException;

public interface ExceptionSupplier<T> {
    T get() throws ParseException;
}
