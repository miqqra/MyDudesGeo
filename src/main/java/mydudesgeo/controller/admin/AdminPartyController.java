package mydudesgeo.controller.admin;

import lombok.RequiredArgsConstructor;
import mydudesgeo.service.PartyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/api/mydudes/geo/party")
public class AdminPartyController {

    private final PartyService service;

    //todo

}
