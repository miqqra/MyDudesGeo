package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.entity.security.UserCredentials;
import mydudesgeo.exception.ClientException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContextService {
    //todo registration

    public static String getCurrentUser() {
        return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getCredentials)
                .map(v -> (UserCredentials) v)
                .map(UserCredentials::getUsername)
                .orElseThrow(() -> ClientException.of(HttpStatus.FORBIDDEN, "Клиент не авторизован"));
    }
}
