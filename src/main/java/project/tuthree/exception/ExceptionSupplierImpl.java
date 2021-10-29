package project.tuthree.exception;


import org.springframework.stereotype.Controller;

import java.text.ParseException;

@Controller
public class ExceptionSupplierImpl implements ExceptionSupplier {

    @Override
    public Object get() throws ParseException {
        return null;
    }

    public static <T> T wrap(ExceptionSupplier<T> z) {
        try {
            return z.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
