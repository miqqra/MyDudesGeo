package mydudesgeo.dataservice;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyVisitMapper;
import mydudesgeo.model.PartyVisitModel;
import mydudesgeo.repository.PartyVisitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartyVisitDataService {

    private final PartyVisitRepository partyVisitRepository;

    private final PartyVisitMapper mapper;

    @Transactional
    public PartyVisitModel create(PartyVisitModel model) {
        //todo if user can see that party

        if (partyVisitRepository.existsByUser_IdAndParty_Id(model.getUser().getId(), model.getParty().getId())) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы уже запланировали посещение этого мероприятия");
        }

        return Optional.of(model)
                .map(mapper::toEntity)
                .map(partyVisitRepository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public void deleteParty(Long userId, Long partyId) {
        if (!partyVisitRepository.existsByUser_IdAndParty_Id(userId, partyId)) {
            throw ClientException.of(HttpStatus.BAD_REQUEST, "Вы не планируете посетить это мероприятие");
        }

        partyVisitRepository.deleteByUser_IdAndParty_Id(userId, partyId);
    }
}
