package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.dto.party.CreatePartyDto;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.dto.party.PartyShortInfoDto;
import mydudesgeo.dto.party.UpdatePartyDto;
import mydudesgeo.dto.party.UpdateVisibilityDto;
import mydudesgeo.service.PartyService;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/party")
public class UserPartyController {

    private final PartyService service;

    @Operation(summary = "Получение информации обо всех мероприятиях в радиусе r километров")
    @PostMapping("/around")
    public List<PartyShortInfoDto> getParties(
            @Parameter(description = "Радиус, в котором будут искаться меропрятия (в километрах)", required = true) @RequestParam Double radius,
            @RequestBody Location point) {
        return service.getPartiesAround(radius, point);
    }

    @Operation(summary = "Поиск мероприятий по названия мероприятия или по организатору")
    @GetMapping("/find")
    public List<PartyShortInfoDto> findParties(@Parameter(description = "Поле для поиска") @RequestParam String search) {
        return service.findParties(search);
    }

    @Operation(summary = "Создание мероприятия")
    @PostMapping
    public PartyDto createParty(@Validated @RequestBody CreatePartyDto dto) {
        return service.createParty(dto);
    }

    @Operation(summary = "Получение информации об определенном мероприятии")
    @GetMapping
    public PartyDto getParty(
            @Parameter(description = "id мероприятия", required = true) @RequestParam Long id) {
        return service.getParty(id);
    }

    @Operation(summary = "Получение информации о мероприятиях от определенного пользователя")
    @GetMapping("/{user}")
    public List<PartyDto> getUsersParties(
            @Parameter(description = "id пользователя", required = true) @PathVariable String user) {
        return service.getUserParties(user);
    }

    @Operation(summary = "Редактирование мероприятия")
    @PutMapping("/{id}")
    public PartyDto updateParty(@Parameter(description = "Id мероприятия", required = true) @PathVariable Long id,
                                @Validated @RequestBody UpdatePartyDto dto) {
        return service.updateParty(id, dto);
    }

    @Operation(summary = "Редактирование видимости мероприятия")
    @PutMapping("/visibility")
    public PartyDto updatePartyVisibility(@Validated @RequestBody UpdateVisibilityDto dto) {
        return service.updatePartyVisibility(dto);
    }

    @Operation(summary = "Удаление мероприятия")
    @DeleteMapping("/{id}")
    public void deleteParty(@Parameter(description = "Id мероприятия", required = true) @PathVariable Long id) {
        service.deleteParty(id);
    }


    @PutMapping(
            path = "/photo/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Изменить фото мероприятия")
    public void changePhoto(@Parameter(description = "Фото") @RequestParam MultipartFile photo,
                            @Parameter(description = "Id мероприятия") @RequestParam Long id) {
        service.changePhoto(photo, id);
    }

    @GetMapping(
            path = "/photo/{id}",
            produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Получение фото мероприятия")
    public byte[] getPhoto(@Parameter(description = "Id мероприятия") @RequestParam Long id) {
        return service.getPhoto(id);
    }

    @DeleteMapping("/photo/{id}")
    @Operation(summary = "Удалить фото мероприятия")
    public void deletePhoto(@Parameter(description = "Id мероприятия") @RequestParam Long id) {
        service.deletePhoto(id);
    }
}
