package mydudesgeo.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dataservice.UserLocationDataService;
import mydudesgeo.dto.user.UserLocationDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.UserLocationMapper;
import mydudesgeo.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final UserLocationDataService dataService;
    private final UserDataService userDataService;

    private final UserLocationMapper mapper;

    public void updateLocation(Location newLocation) {
        String name = UserCredentialsService.getCurrentUser();

        UserModel userModel = userDataService.getInfo(name);

        if (!dataService.existsByName(name)) {
            dataService.createUser(userModel, newLocation);
        }

        Optional.of(name)
                .map(userName -> dataService.updateLocation(name, newLocation))
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Такого пользователя не существует"));
    }

    public UserLocationDto getLocation(String user) {
        UserModel userModel = userDataService.getInfo(user);

        return Optional.of(user)
                .map(dataService::getLocation)
                .map(v -> mapper.toDto(v, userModel))
                .orElse(null);
    }

    //todo move to userService
    public void changeFreezeToggle(Boolean freeze) {
        String name = UserCredentialsService.getCurrentUser();

        userDataService.changeFreezeToggle(name, freeze);
    }
}
