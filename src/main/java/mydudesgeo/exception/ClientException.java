package mydudesgeo.exception;

import org.springframework.http.HttpStatus;

public class ClientException extends RuntimeException {
    private HttpStatus code;
    private String message;

    private ClientException(HttpStatus code, String message){
        this.code = code;
        this.message = message;
    }

    public static ClientException of(HttpStatus code, String message){
        return new ClientException(code, message);
    }
}
