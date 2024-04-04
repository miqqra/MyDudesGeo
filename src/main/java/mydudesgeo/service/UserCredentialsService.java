package mydudesgeo.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.user.LoginUserDto;
import mydudesgeo.dto.user.RegistrateUserDto;
import mydudesgeo.entity.security.UserCredentials;
import mydudesgeo.exception.ClientException;
import mydudesgeo.model.UserModel;
import mydudesgeo.service.security.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCredentialsService {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserDataService userDataService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public static String getCurrentUser() {
        return "Klim";
//        return Optional.of(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .map(Principal::getName)
//                .orElseThrow(() -> ClientException.of(HttpStatus.FORBIDDEN, "Клиент не авторизован"));
    }

    public boolean registerUser(RegistrateUserDto dto, HttpServletRequest httpServletRequest) {
        UserCredentials userCredentials = new UserCredentials()
                .setPassword(dto.getPassword())
                .setUsername(dto.getNickname());

        UserModel userModel = new UserModel()
                .setNickname(dto.getNickname())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName());

        userDetailsService.saveUser(userCredentials);
        userDataService.create(userModel);

        authenticate(userCredentials, dto.getPassword(), httpServletRequest);

        return true;
    }

    public boolean loginUser(LoginUserDto dto, HttpServletRequest httpServletRequest) {
        UserDetails userCredentials = userDetailsService.loadUserByUsername(dto.getLogin());

        if (Objects.isNull(userCredentials)
                || passwordEncoder.matches(userCredentials.getPassword(), dto.getPassword())) {
            throw ClientException.of(HttpStatus.NOT_FOUND, "Неправильный логин или пароль");
        }

        authenticate(userCredentials, dto.getPassword(), httpServletRequest);

        return true;
    }

    public void logoutUser(HttpServletRequest httpServletRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        SecurityContextHolder.getContext().setAuthentication(null);
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    private void authenticate(UserDetails userCredentials, String password, HttpServletRequest httpServletRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userCredentials,
                        password,
                        userCredentials.getAuthorities()
                );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            HttpSession httpSession = httpServletRequest.getSession(true);
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }
    }
}
