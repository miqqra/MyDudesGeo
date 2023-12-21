package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Point;
import mydudesgeo.data.Visibility;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.PartyModel;
import mydudesgeo.repository.PartyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public PartyModel getParty(Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(mapper::toModel)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PartyModel> getPartiesAround(Integer radius, Point location) {
        return Optional.of(radius)
                .map(r -> repository.getPartiesAround(r, location))
                .stream()
                .flatMap(Collection::stream)
                .map(mapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PartyModel> getUserParties(String user) {
        return Optional.ofNullable(user)
                .map(repository::findAllByCreator)
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
                .map(party -> party
                        .setName(dto.getName())
                        .setDescription(dto.getDescription())
                        .setLocation(dto.getLocation())
                        .setLimits(dto.getLimits())
                )
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
}
