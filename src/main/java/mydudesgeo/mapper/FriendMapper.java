package mydudesgeo.mapper;

import mydudesgeo.data.Visibility;
import mydudesgeo.dto.friend.FriendsDto;
import mydudesgeo.entity.friends.CloseFriends;
import mydudesgeo.entity.friends.Friends;
import mydudesgeo.model.FriendModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = {
        UserMapper.class
})
public abstract class FriendMapper {

    @Autowired
    private UserMapper userMapper;

    @Mapping(target = "person.nickname", source = "person")
    @Mapping(target = "friend.nickname", source = "friend")
    public abstract CloseFriends toEntityCloseFriends(String person, String friend);

    @Mapping(target = "person.nickname", source = "person")
    @Mapping(target = "friend.nickname", source = "friend")
    public abstract Friends toEntityFriends(String person, String friend);

    @Mapping(target = "person", ignore = true)
    public abstract FriendModel toModelFriends(List<Friends> source, String person, Visibility visibility);

    @AfterMapping
    protected void postMap(@MappingTarget FriendModel target, List<Friends> source, Visibility visibility) {
        target.setPerson(userMapper.toModel(
                source.isEmpty()
                        ? null
                        : source.getFirst().getPerson()));
    }

    @Mapping(target = "person", ignore = true)
    public abstract FriendModel toModelCloseFriends(List<CloseFriends> source, String person, Visibility visibility);

    @AfterMapping
    protected void postMapCloseFriends(@MappingTarget FriendModel target, List<CloseFriends> source, Visibility visibility) {
        target.setPerson(userMapper.toModel(
                source.isEmpty()
                        ? null
                        : source.getFirst().getPerson()));
    }

    public abstract FriendsDto toDto(FriendModel source);
}
