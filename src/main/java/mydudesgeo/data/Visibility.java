package mydudesgeo.data;

import java.util.Optional;

public enum Visibility {
    //todo add fully private parties
    FRIENDS,
    ALL,
    CLOSE_FRIENDS;

    public static Visibility getEnum(String visibility) {
        return Optional.of(visibility)
                .map(Visibility::valueOf)
                .orElse(null);
    }

    public static String getName(Visibility visibility) {
        return Optional.of(visibility)
                .map(Visibility::getName)
                .orElse(null);
    }
}
