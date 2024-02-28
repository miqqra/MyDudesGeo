package mydudesgeo.data;

import jakarta.persistence.Converter;
import mydudesgeo.common.EnumConverter;

public enum FriendRequestStatus {
    ACCEPTED,
    REJECTED,
    NOT_VIEWED;

    @Converter(autoApply = true)
    public static class FriendRequestConverter extends EnumConverter<FriendRequestStatus> {
        public FriendRequestConverter() {
            super(FriendRequestStatus.class, NOT_VIEWED); //add none
        }
    }
}
