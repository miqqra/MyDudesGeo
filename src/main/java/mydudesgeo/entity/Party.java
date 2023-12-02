package mydudesgeo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mydudesgeo.data.Visibility;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.geo.Point;

import java.time.ZonedDateTime;
import java.util.List;

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

    @Column(name = "category")
    private String category;

    @Column(name = "location")
    @JdbcTypeCode(SqlTypes.JSON)
    private Point location;

    //todo fk on geo_users
    @Column(name = "creator")
    private String creator;

    @Column(name = "participants")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> participants;

    @Column(name = "limits")
    private Integer limits;

    @Column(name = "visibility")
    private Visibility visibility;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;
}
