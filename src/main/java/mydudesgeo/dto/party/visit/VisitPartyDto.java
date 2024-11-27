package mydudesgeo.dto.party.visit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VisitPartyDto {

    @Schema(description = "Id мероприятия")
    @NotNull
    private Long partyId;

}
