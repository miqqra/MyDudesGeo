package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dataservice.UserLocationDataService;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.UserLocationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final UserLocationDataService dataService;

    private final UserLocationMapper mapper;

    public void updateLocation(String name, Location newLocation) {
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
