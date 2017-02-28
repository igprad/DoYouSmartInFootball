package com.android.alz.doyousmartinfootball.api;

/**
 * Created by ALz on 2/15/2017.
 */

public interface FootballData {
    String END_POINT = "http://api.football-data.org/v1/";
    String KEY = "7f08807927df41c4b32ea4a986ad764c";
    String COMPETITIONS = "competitions/";
    //================Competitions ID========================
    String COMP_EUROPEAN_CHAMPIONSHIPS_FRANCE="424/";
    String COMP_PREMIERE_LEAGUE = "426/";
    String COMP_CHAMPIONSHIP = "427/";
    String COMP_LEAGUE_ONE="428/";
    String COMP_BUNDESLIGA_1="430/";
    String COMP_BUNDESLIGA_2="431/";
    String COMP_DFB_POKAL="432/";
    String COMP_ERIDIVISE="433/";
    String COMP_LIGUE_1="434/";
    String COMP_LIGUE_2="435/";
    String COMP_PRIMERA_DIVSION="436/";
    String COMP_ADELANTE_LIGA="437/";
    String COMP_SERIE_A="438/";
    String COMP_PRIMEIRA_LIGA="439/";
    String COMP_CHAMPIONS_LEAGUE="440/";
    //===============TEAMS===================================
    String TEAMS  ="teams/";
    String MANCHESTER_UNITED = "66";
}
