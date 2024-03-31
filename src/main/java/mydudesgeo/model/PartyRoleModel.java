package mydudesgeo.model;

import lombok.Data;

@Data
public class PartyRoleModel {

    private Long id;
    private String name;
    private String description;
    private PartyModel party;
}
