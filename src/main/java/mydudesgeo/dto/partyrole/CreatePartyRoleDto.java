package mydudesgeo.dto.partyrole;

import lombok.Data;

@Data
public class CreatePartyRoleDto {

    private String name;
    private String description;
    private Long partyId;
}
