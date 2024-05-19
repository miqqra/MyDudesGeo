package mydudesgeo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientException extends RuntimeException {
    private final HttpStatus code;
    private final String message;

    private ClientException(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ClientException of(HttpStatus code, String message) {
        return new ClientException(code, message);
    }
}
