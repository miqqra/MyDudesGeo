package mydudesgeo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mydudesgeo.data.Visibility;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "geo_parties")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Party {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category", referencedColumnName = "category")
    private PartyCategory category;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "nickname")
    private User creator;

    @Column(name = "participants")
    @JdbcTypeCode(SqlTypes.JSON)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "geo_users_to_parties",
            joinColumns = @JoinColumn(name = "party_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "username", referencedColumnName = "nickname"))
    private List<User> participants;

    @Column(name = "limits")
    private Integer limits;

    @Column(name = "visibility")
    @Enumerated(EnumType.STRING)
    @Convert(converter = Visibility.VisibilityConverter.class)
    private Visibility visibility;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", referencedColumnName = "id", updatable = false)
    private List<PartyRole> roles;

    @Column(name = "link_dobro")
    private String linkDobro;

    @Column(name = "chat_id_telegram")
    private Long chatIdTelegram; //todo private chat id long instead

    @Column(name = "photo")
    @Lob
    private byte[] photo; //todo
}
