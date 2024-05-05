package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
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
    public PartyDto createPartyFromDobroRu(@RequestHeader String url) {
        return service.createPartyFromDobroRu(url);
    }

    @PostMapping("/vk/migrate")
    @Operation(summary = "Создание мероприятия в MyDudes на основе мероприятия VK")
    public PartyDto migratePartyFromVk() {
        return service.migratePartyFromVk();
    }

    @PostMapping("/vk/create")
    @Operation(summary = "Создание мероприятия в VK на основе мероприятия MyDudes")
    public PartyDto createPartyInVk() {
        return service.createPartyInVk();
    }

}
