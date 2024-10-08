package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.party.visit.VisitPartyDto;
import mydudesgeo.dto.user.UserDto;
import mydudesgeo.service.PartyVisitService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/party/visit")
public class PartyVisitController {

    private final PartyVisitService service;

    //todo move to main request
    @GetMapping("/friends")
    @Operation(description = "Показать друзей, которые планируют посетить мероприетие")
    public List<UserDto> getFriendsWhoVisitedParty(@Parameter(description = "Id мероприятия") @RequestParam Long partyId) {
        return service.getFriendsWhoVisitedParty(partyId);
    }

    //todo добавить флаг визита мероприятия в дто парти

    @PostMapping
    @Operation(description = "Запланировать визит на мероприятие")
    public void visitParty(@Validated @RequestBody VisitPartyDto dto) {
        service.visitParty(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Отменить запланированный визит")
    public void deleteVisit(@Parameter(description = "Id мероприятия") @PathVariable Long id) {
        service.deleteVisit(id);
//todo сделать проверку, что мероприятие еще не закончено
    }
}
