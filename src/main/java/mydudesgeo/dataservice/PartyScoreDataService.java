package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.repository.PartyScoreRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyScoreDataService {

    private final PartyScoreRepository partyScoreRepository;
}
