package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.repository.PartyCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyCategoryDataService {

    private final PartyCategoryRepository repository;
}
