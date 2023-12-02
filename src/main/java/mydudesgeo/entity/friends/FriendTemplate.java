package mydudesgeo.entity.friends;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class FriendTemplate {

    @Column(name = "person")
    private String person;

    @Column(name = "friend")
    private String friend;
}
