package mydudesgeo.data;

import java.util.Optional;

public enum Visibility {
    FRIENDS,
    ALL;

    public static Visibility getEnum(String visibility) {
        return Optional.of(visibility)
                .map(Visibility::valueOf)
                .orElse(Visibility.ALL);
    }
}
