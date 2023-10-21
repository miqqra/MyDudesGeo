package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository repository;
}
