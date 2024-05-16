package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mydudesgeo.dto.party.PartyDto;
import mydudesgeo.service.PartyIntegrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mydudes/geo/party/integrations")
public class IntegrationController {

    private final PartyIntegrationService service;

    @PostMapping("/dobro/ru")
    @Operation(summary = "Создание мероприятия из Добро.ру")
    public PartyDto createPartyFromDobroRu(
            @RequestHeader @Parameter(description = "Ссылка на мероприятие в dobro.ru") String url) {
        return service.createPartyFromDobroRu(url);
    }

}
