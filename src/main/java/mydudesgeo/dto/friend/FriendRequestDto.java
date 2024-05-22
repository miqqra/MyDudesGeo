package mydudesgeo.dto.friend;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendRequestDto {

    @NotNull
    private Long id;

    @NotNull
    private String requestFrom;

    @NotNull
    private String requestTo;

}
