package com.example.elijah.golfplayertimemanagement;

public class HoleDifficulty {
    String Difficulty;
    String Desctiption;
    String Par;
    String Tips;
    String Yards;

    public HoleDifficulty(String difficulty, String desctiption, String par, String tips, String yards) {
        Difficulty = difficulty;
        Desctiption = desctiption;
        Par = par;
        Tips = tips;
        Yards = yards;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public String getDesctiption() {
        return Desctiption;
    }

    public void setDesctiption(String desctiption) {
        Desctiption = desctiption;
    }

    public String getPar() {
        return Par;
    }

    public void setPar(String par) {
        Par = par;
    }

    public String getTips() {
        return Tips;
    }

    public void setTips(String tips) {
        Tips = tips;
    }

    public String getYards() {
        return Yards;
    }

    public void setYards(String yards) {
        Yards = yards;
    }

    @Override
    public String toString() {
        return "HoleDifficulty{" +
                "Difficulty='" + Difficulty + '\'' +
                ", Desctiption='" + Desctiption + '\'' +
                ", Par='" + Par + '\'' +
                ", Tips='" + Tips + '\'' +
                ", Yards='" + Yards + '\'' +
                '}';
    }
}
