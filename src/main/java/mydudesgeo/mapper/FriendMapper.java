package mydudesgeo.mapper;

import mydudesgeo.data.Visibility;
import mydudesgeo.dto.friend.FriendsDto;
import mydudesgeo.entity.friends.CloseFriends;
import mydudesgeo.entity.friends.Friends;
import mydudesgeo.model.FriendModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public abstract class FriendMapper {

    public abstract CloseFriends toEntityCloseFriends(String person, String friend);

    public abstract Friends toEntityFriends(String person, String friend);

    @Mapping(target = "friends", source = "source", qualifiedByName = "mapFriends")
    public abstract FriendModel toModelFriends(List<Friends> source, String person, Visibility visibility);

    @Mapping(target = "friends", source = "source", qualifiedByName = "mapCloseFriends")
    public abstract FriendModel toModelCloseFriends(List<CloseFriends> source, String person, Visibility visibility);

    @Named("mapFriends")
    protected List<String> mapFriends(List<Friends> source) {
        return source.stream().map(Friends::getFriend).toList();
    }

    @Named("mapCloseFriends")
    protected List<String> mapCloseFriends(List<CloseFriends> source) {
        return source.stream().map(CloseFriends::getFriend).toList();
    }

    public abstract FriendsDto toDto(FriendModel source);
}
