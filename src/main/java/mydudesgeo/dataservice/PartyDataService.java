package mydudesgeo.dataservice;

import lombok.RequiredArgsConstructor;
import mydudesgeo.data.Visibility;
import mydudesgeo.exception.ClientException;
import mydudesgeo.mapper.PartyMapper;
import mydudesgeo.model.PartyModel;
import mydudesgeo.repository.PartyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.ImageProducer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyDataService {

    private final PartyRepository repository;

    private final PartyMapper mapper;

    @Transactional
    public PartyModel updatePartyVisibility(Long id, Visibility visibility){
        PartyModel party = Optional.of(id)
                .flatMap(repository::findById)
                .map(mapper::toModel)
                .map(partyModel -> partyModel.setVisibility(visibility))
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toModel)
                .orElseThrow(() -> ClientException.of(HttpStatus.NOT_FOUND, "Мероприятие не найдено"));
    }
}
