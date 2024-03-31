package mydudesgeo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "geo_users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "birthdate")
    private LocalDate birthDate; //todo make format

    @Column(name = "sex")
    private String sex; //todo make enum?

    @Column(name = "info")
    private String info;

    @Column(name = "photo")
    @Lob
    private byte[] photo;

    @Column(name = "freeze_location")
    private boolean freezeLocation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "geo_users_to_hobbies",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "nickname"),
            inverseJoinColumns = @JoinColumn(name = "hobby_name", referencedColumnName = "name"))
    private List<Hobby> hobbies;
}
