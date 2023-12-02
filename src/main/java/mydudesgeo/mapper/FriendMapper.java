package mydudesgeo.mapper;

import mydudesgeo.data.Visibility;
import mydudesgeo.dto.friend.FriendsDto;
import mydudesgeo.entity.friends.FriendTemplate;
import mydudesgeo.model.FriendModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;

@Mapper
public abstract class FriendMapper {

    public abstract FriendTemplate toEntity(String person, String friend);

    @Mapping(target = "friends", ignore = true)
    public abstract FriendModel toModel(List<FriendTemplate> source, String person, Visibility visibility);

    @AfterMapping
    protected void postMap(@MappingTarget FriendModel target, List<FriendTemplate> source, String person, Visibility visibility) {
        Optional.of(source)
                .map(s -> s
                        .stream()
                        .map(FriendTemplate::getFriend)
                        .toList())
                .map(target::setFriends);
    }

    public abstract FriendsDto toDto(FriendModel source);
}
