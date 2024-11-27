package mydudesgeo.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/mydudes/geo/party/score")
public class PartyScoreController {

    @PostMapping
    @Operation(description = "Поставить оценку мероприятию")
    public void setScoreToParty() {

    }

    @PutMapping
    @Operation(description = "Изменить оценку мероприятию")
    public void changeScoreToParty() {

    }

    @DeleteMapping
    @Operation(description = "Убрать оценку мероприятию")
    public void deleteScore() {

    }
}
