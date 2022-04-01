package org.happyhour.scoreboard.model;

import lombok.Data;

@Data
public class AddMatchRequestModel {

    private Integer userId;

    private Integer score;

    private Integer role;
}
