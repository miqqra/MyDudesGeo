package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.data.FriendRequestStatus;

@Data
public class FriendRequestModel {

    private Long id;
    private UserModel requestFrom;
    private UserModel requestTo;
    private FriendRequestStatus status;
}
