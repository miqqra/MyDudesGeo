package mydudesgeo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import mydudesgeo.data.Visibility;

import java.util.Collections;
import java.util.List;

@Data
public class FriendModel {

    private String person;
    private Visibility visibility;
    private List<String> friends;

    @JsonIgnore
    public static FriendModel emptyFriendList(Visibility visibility, String person) {
        return new FriendModel()
                .setVisibility(visibility)
                .setPerson(person)
                .setFriends(Collections.emptyList());
    }
}
