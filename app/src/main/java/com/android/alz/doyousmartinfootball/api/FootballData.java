package com.android.alz.doyousmartinfootball.api;

import java.util.ArrayList;

/**
 * Created by ALz on 2/15/2017.
 */

public interface FootballData {
    String END_POINT = "http://api.football-data.org/v1/";
    String KEY = "7f08807927df41c4b32ea4a986ad764c";
    String COMPETITIONS = "competitions/";
    //================Competitions ID========================
    int COMP_EUROPEAN_CHAMPIONSHIPS_FRANCE=424;
    int COMP_PREMIERE_LEAGUE = 426;
    int COMP_CHAMPIONSHIP = 427;
    int COMP_LEAGUE_ONE=428;
    int COMP_FA_CUP=428;
    int COMP_BUNDESLIGA_1=430;
    int COMP_BUNDESLIGA_2=431;
    int COMP_DFB_POKAL=432;
    int COMP_ERIDIVISE=433;
    int COMP_LIGUE_1=434;
    int COMP_LIGUE_2=435;
    int COMP_PRIMERA_DIVSION=436;
    int COMP_ADELANTE_LIGA=437;
    int COMP_SERIE_A=438;
    int COMP_PRIMEIRA_LIGA=439;
    int COMP_CHAMPIONS_LEAGUE=440;

    //===============TEAMS===================================
    String TEAMS  ="teams/";
    String MANCHESTER_UNITED = "66";
}
