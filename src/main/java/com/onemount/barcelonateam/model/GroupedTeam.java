package com.onemount.barcelonateam.model;

import lombok.Data;

import java.util.List;

@Data
public class GroupedTeam {

    private List<Player> GK;
    private List<Player> DF;
    private List<Player> MF;
    private List<Player> FW;
}
