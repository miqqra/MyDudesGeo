package mydudesgeo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mydudesgeo.data.PointData;
import mydudesgeo.data.Visibility;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity(name = "geo_parties")
@Getter
@Setter
@NoArgsConstructor
public class Party {

    @Id
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
    private PointData location;

    @Column(name = "creator")
    private String creator;

    @Column(name = "participants")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> participants;

    @Column(name = "limits")
    private Integer limits;

    @Column(name = "visibility")
    private Visibility visibility;
}
