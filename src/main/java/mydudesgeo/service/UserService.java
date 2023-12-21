package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Point;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataService dataService;

    private final UserMapper mapper;

    public void updateLocation(String name, Point newLocation) {
        if (!dataService.existsByName(name)) {
            dataService.createUser(name, newLocation);
        }

        Optional.of(name)
                .map(userName -> dataService.updateLocation(userName, newLocation))
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Такого пользователя не существует"));
    }

    public UserDto getLocation(String user) {
        return Optional.of(user)
                .map(dataService::getLocation)
                .map(mapper::toDto)
                .orElse(null);
    }

    public void changeFreezeToggle(String name, Boolean freeze) {
        dataService.changeFreezeToggle(name, freeze);
    }
}
