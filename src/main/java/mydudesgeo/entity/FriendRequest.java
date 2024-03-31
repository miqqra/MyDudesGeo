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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mydudesgeo.data.FriendRequestStatus;

@Entity
@Table(name = "geo_parties")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FriendRequest {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_from", referencedColumnName = "nickname")
    private User requestFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "request_to", referencedColumnName = "nickname")
    private User requestTo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Convert(converter = FriendRequestStatus.FriendRequestConverter.class)
    private FriendRequestStatus status;
}
