package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.data.FriendRequestStatus;

@Data
public class FriendRequestModel {

    private Long id;
    private String requestFrom;
    private String requestTo;
    private FriendRequestStatus status;
}
