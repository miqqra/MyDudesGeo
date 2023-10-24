package mydudesgeo.controller.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyLocationDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.service.PartyCategoryService;
import mydudesgeo.service.PartyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/party")
public class UserPartyController {

    private final PartyService partyService;
    private final PartyCategoryService partyCategoryService;


    @Operation(description = "Получение информации обо всех мероприятиях в радиусе r километров")
    @GetMapping
    public List<PartyLocationDto> getParties(
            @Parameter(description = "Радиус, в котором будут искаться меропрятия", required = true) @RequestParam Integer r,
            @Parameter(description = "Координата пользователя", required = true) @RequestParam Double x,
            @Parameter(description = "Координата пользователя", required = true) @RequestParam Double y) {
        return partyService.getPartiesAround(r, x, y);
    }

    @Operation(description = "Получение информации об определенном мероприятии")
    @GetMapping
    public void getParty(
            @Parameter(description = "id мероприятия", required = true)
            @RequestParam Integer id) {

    }

    @Operation(description = "Получение информации о мероприятиях от определенного пользователя")
    @GetMapping
    public void getUsersParties(
            @Parameter(description = "id пользователя", required = true)
            @RequestParam Long user) {

    }

    @Operation(description = "Редактирование мероприятия")
    @PutMapping
    public void updateParty() {

    }

    @Operation(description = "Редактирование видимости мероприятия")
    @PutMapping("/visibility")
    public PartyDto updatePartyVisibility(@Validated @RequestBody UpdateVisibilityDto dto) {
        return partyService.updatePartyVisibility(dto);
    }


}
