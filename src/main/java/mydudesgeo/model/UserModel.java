package mydudesgeo.model;

import lombok.Data;
import mydudesgeo.entity.Hobby;

import java.time.LocalDate;
import java.util.List;

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

}
