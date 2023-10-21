package mydudesgeo.service;

import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.PartyCategoryDataService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyCategoryService {

    private final PartyCategoryDataService dataService;
}
