package mydudesgeo.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TgNotifies {

    PARTY_UPDATE("Мероприятие \"%s\" было изменено."),
    NEW_PARTICIPANT("К мероприятию %s присоединился новый участник."),
    CHANGE_LOCATION("Локация мероприятия \"%s\" была изменена. Новая локация:"),
    CHANGE_PHOTO("В мероприятии \"%s\" было изменено фото. Посмотрите скорее");

    private final String message;
}
