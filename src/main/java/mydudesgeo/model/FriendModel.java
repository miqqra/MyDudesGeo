package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.data.Visibility;

import java.util.List;

@Data
public class FriendModel {

    private UserModel person;
    private Visibility visibility;
    private List<UserModel> friends;

}
