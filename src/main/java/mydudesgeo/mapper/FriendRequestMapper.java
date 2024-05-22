package mydudesgeo.mapper;

import mydudesgeo.dto.friend.FriendRequestDto;
import mydudesgeo.entity.FriendRequest;
import mydudesgeo.model.FriendRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {
        UserMapper.class
})
public abstract class FriendRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestFrom.nickname", source = "personFrom")
    @Mapping(target = "requestTo.nickname", source = "personTo")
    @Mapping(target = "status", ignore = true)
    public abstract FriendRequestModel toModel(String personFrom, String personTo);

    public abstract FriendRequest toEntity(FriendRequestModel source);

    public abstract FriendRequestModel toModel(FriendRequest source);

    @Mapping(target = "requestFrom", source = "requestFrom.nickname")
    @Mapping(target = "requestTo", source = "requestTo.nickname")
    public abstract FriendRequestDto toDto(FriendRequestModel source);
}
