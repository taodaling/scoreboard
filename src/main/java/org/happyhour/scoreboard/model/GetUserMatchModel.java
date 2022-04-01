package org.happyhour.scoreboard.model;

import lombok.Data;

import java.util.List;

@Data
public class GetUserMatchModel {

    private Integer userId;

    List<Usermatch> usermatches;
}
