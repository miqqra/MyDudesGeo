package mydudesgeo.dataservice;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.data.Visibility;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.PartyModel;
import mydudesgeo.repository.PartyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartyDataService {

    private final PartyRepository repository;

    private final PartyMapper mapper;

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByLink(String link) {
        return repository.existsByLinkDobro(link);
    }

    @Transactional(readOnly = true)
    public PartyModel getParty(Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PartyModel> getPartiesAround(Location location, Double radius) {
        return Optional.of(radius)
                .map(r -> repository.getPartiesAround(location.getLatitude(), location.getLongitude(), radius))
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PartyModel> getUserParties(String user) {
        return Optional.ofNullable(user)
                .map(repository::findByCreatorNickname)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PartyModel> searchParties(String search) {
        return Optional.of(search)
                .map(repository::searchPartiesByCreatorOrName)
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    @Transactional
    public PartyModel createParty(PartyModel party) {
        return Optional.of(party)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public PartyModel updateParty(Long id, UpdatePartyDto dto) {
        return Optional.of(id)
                .flatMap(repository::findById)
                .map(v -> mapper.toEntity(v, dto))
                .map(repository::save)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional
    public PartyModel updatePartyVisibility(Long id, Visibility visibility) {
        return Optional.of(id)
                .flatMap(repository::findById)
                .map(party -> party.setVisibility(visibility))
                .map(repository::save)
                .map(mapper::toModel)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }

    @Transactional
    public void deleteParty(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void changePhoto(byte[] file, Long id) {
        repository.updatePhoto(file, id);
    }

    @Transactional
    public void deletePhoto(Long id) {
        repository.deletePhotoByNickname(id);
    }
}
