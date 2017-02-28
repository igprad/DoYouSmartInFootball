package com.android.alz.doyousmartinfootball.model;

/**
 * Created by ALz on 2/21/2017.
 */

public class Competition {
    int id,currentMatchDay,numberOfMatches,numberOfTeams,numberOfGames;
    String caption,league,year,lastUpdated;

    public Competition(){}

    public Competition(int id,
                       int currentMatchDay, int numberOfMatches, int numberOfTeams, int numberOfGames,
                       String caption, String league, String year, String lastUpdated) {
        this.id = id;
        this.currentMatchDay = currentMatchDay;
        this.numberOfMatches = numberOfMatches;
        this.numberOfTeams = numberOfTeams;
        this.numberOfGames = numberOfGames;
        this.caption = caption;
        this.league = league;
        this.year = year;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentMatchDay() {
        return currentMatchDay;
    }

    public void setCurrentMatchDay(int currentMatchDay) {
        this.currentMatchDay = currentMatchDay;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
