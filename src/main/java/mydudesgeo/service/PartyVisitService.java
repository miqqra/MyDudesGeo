package mydudesgeo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dataservice.FriendsDataService;
import mydudesgeo.dataservice.PartyVisitDataService;
import mydudesgeo.dto.party.visit.VisitPartyDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.mapper.PartyVisitMapper;
import mydudesgeo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyVisitService {

    private final UserService userService;

    private final FriendsDataService friendsDataService;
    private final PartyVisitDataService dataService;

    private final PartyVisitMapper mapper;
    private final UserMapper userMapper;

    public void visitParty(VisitPartyDto dto) {
        Optional.ofNullable(dto)
                .map(mapper::toModel)
                .map(dataService::create)
                .orElse(null);
    }

    public void deleteVisit(Long partyId) {
        Optional.of(userService)
                .map(UserService::getCurrentUserInfo)
                .map(UserDto::getId)
                .ifPresent(userId -> dataService.deleteParty(userId, partyId));
    }

    public List<UserDto> getFriendsWhoVisitedParty(Long partyId) {
        return Optional.of(userService)
                .map(UserService::getCurrentUserInfo)
                .map(UserDto::getId)
                .map(userId -> friendsDataService.getFriendsWhoVisitedParty(userId, partyId))
                .stream()
                .flatMap(Collection::stream)
                .map(userMapper::toDto)
                .toList();
    }
}
