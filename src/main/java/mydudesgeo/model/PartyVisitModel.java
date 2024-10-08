package mydudesgeo.model;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class PartyVisitModel {

    private Long id;
    private UserModel user;
    private PartyModel party;
    private ZonedDateTime date;
}
