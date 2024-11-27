package mydudesgeo.mapper;

import java.time.ZonedDateTime;
import java.util.Optional;
import mydudesgeo.dataservice.UserDataService;
import mydudesgeo.dto.party.visit.VisitPartyDto;
import mydudesgeo.entity.PartyVisit;
import mydudesgeo.model.PartyVisitModel;
import mydudesgeo.service.UserCredentialsService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = {
        PartyMapper.class,
        UserMapper.class
})
public abstract class PartyVisitMapper {

    @Autowired
    private UserDataService userDataService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "party", source = "dto.partyId")
    @Mapping(target = "date", ignore = true)
    public abstract PartyVisitModel toModel(VisitPartyDto dto);

    @AfterMapping
    protected void postMap(@MappingTarget PartyVisitModel target, VisitPartyDto dto) {
        target.setDate(ZonedDateTime.now());
        Optional.of(UserCredentialsService.getCurrentUser())
                .map(userDataService::getInfo)
                .ifPresent(target::setUser);
    }

    public abstract PartyVisit toEntity(PartyVisitModel source);

    public abstract PartyVisitModel toModel(PartyVisit source);
}
