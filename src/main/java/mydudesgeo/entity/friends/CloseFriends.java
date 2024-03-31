package mydudesgeo.entity.friends;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mydudesgeo.entity.User;

@Entity
@Table(name = "geo_close_friends")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CloseFriends extends FriendTemplate {
    //todo change to many to many

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person", referencedColumnName = "nickname")
    private User person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend", referencedColumnName = "nickname")
    private User friend;
}
