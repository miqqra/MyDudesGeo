package mydudesgeo.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/api/mydudes/geo/party")
public class AdminPartyController {

    @Operation(description = "")
    @GetMapping
    public void updateParty() {

    }

    @Operation(description = "")
    @PutMapping("/visibility")
    public void updatePartyVisibility() {

    }

}
