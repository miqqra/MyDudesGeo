package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyShortInfoDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.service.PartyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    private final PartyService service;

    @Operation(description = "Получение информации обо всех мероприятиях в радиусе r километров")
    @PostMapping("/around")
    public List<PartyShortInfoDto> getParties(
            @Parameter(description = "Радиус, в котором будут искаться меропрятия", required = true) @RequestParam Double r,
            @RequestBody Location point) {
        return service.getPartiesAround(r, point);
    }

    @Operation(description = "Поиск мероприятий по названия мероприятия или по организатору")
    @GetMapping("/find")
    public List<PartyShortInfoDto> findParties(@Parameter(description = "Поле для поиска") @RequestParam String search) {
        return service.findParties(search);
    }

    @Operation(description = "Создание мероприятия")
    @PostMapping
    public PartyDto createParty(@Validated @RequestBody CreatePartyDto dto) {
        return service.createParty(dto);
    }

    @Operation(description = "Получение информации об определенном мероприятии")
    @GetMapping
    public PartyDto getParty(
            @Parameter(description = "id мероприятия", required = true) @RequestParam Long id) {
        return service.getParty(id);
    }

    @Operation(description = "Получение информации о мероприятиях от определенного пользователя")
    @GetMapping("/{user}")
    public List<PartyDto> getUsersParties(
            @Parameter(description = "id пользователя", required = true) @PathVariable String user) {
        return service.getUserParties(user);
    }

    @Operation(description = "Редактирование мероприятия")
    @PutMapping("/{id}")
    public PartyDto updateParty(@Parameter(description = "Id мероприятия", required = true) @PathVariable Long id,
                                @Validated @RequestBody UpdatePartyDto dto) {
        return service.updateParty(id, dto);
    }

    @Operation(description = "Редактирование видимости мероприятия")
    @PutMapping("/visibility")
    public PartyDto updatePartyVisibility(@Validated @RequestBody UpdateVisibilityDto dto) {
        return service.updatePartyVisibility(dto);
    }

    @Operation(description = "Удаление мероприятия")
    @DeleteMapping("/{id}")
    public void deleteParty(@Parameter(description = "Id мероприятия", required = true) @PathVariable Long id) {
        service.deleteParty(id);
    }
}
