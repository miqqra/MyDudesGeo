package mydudesgeo.mapper;

import mydudesgeo.entity.FriendRequest;
import mydudesgeo.model.FriendRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class FriendRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestTo", constant = "changeIt")
    //todo change for current user
    @Mapping(target = "status", ignore = true)
    public abstract FriendRequestModel toModel(String personFrom);

    public abstract FriendRequest toEntity(FriendRequestModel source);

    public abstract FriendRequestModel toModel(FriendRequest source);
}
