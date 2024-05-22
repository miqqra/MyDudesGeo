package mydudesgeo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;
import mydudesgeo.entity.Hobby;
import org.apache.commons.lang3.StringUtils;

@Data
public class UserModel {

    private Long id;
    private String nickname;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String sex;
    private String info;
    private byte[] photo;
    private boolean freezeLocation;
    private List<Hobby> hobbies;
    private String telegramNick;
    private Long telegramChatId;

    public String getFullName() {
        return Stream.of(firstName, lastName)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(" "));
    }

}
