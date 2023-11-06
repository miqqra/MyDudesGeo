package mydudesgeo.data;

import java.util.Optional;

public enum Visibility {
    FRIENDS,
    ALL;
    //todo custom visibility

    public static Visibility getEnum(String visibility) {
        return Optional.of(visibility)
                .map(Visibility::valueOf)
                .orElse(Visibility.ALL);
    }
}
