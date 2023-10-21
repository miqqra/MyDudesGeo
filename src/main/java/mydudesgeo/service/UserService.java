package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.UserDataService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataService dataService;
}
