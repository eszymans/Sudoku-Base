package komponentowe.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppException extends Exception {

    private static final ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.getDefault());

    public AppException(String key) {
        super(messages.getString(key));
    }

    public AppException(String key, Throwable cause) {
        super(messages.getString(key), cause);
    }
}

