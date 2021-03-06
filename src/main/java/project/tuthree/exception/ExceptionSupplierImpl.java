package project.tuthree.exception;


import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class ExceptionSupplierImpl implements ExceptionSupplier {

    @Override
    public Object get() throws ParseException, IOException {
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
