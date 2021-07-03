package com.example.kimetsuwiki;

public class Character {
    private final int id;
    private final String name;
    private final String shortInfo;
    private final String relation;
    private final String fightExp;
    private final int avatar;

    public Character() {
        this.id = -1;
        this.name = "placeholder";
        this.shortInfo = "placeholder";
        this.relation = "placeholder";
        this.fightExp = "placeholder";
        this. avatar = -1;
    }

    public Character(int id, String name, String shortInfo, String relation, String fightExp, int avatar) {
        this.id = id;
        this.name = name;
        this.shortInfo = shortInfo;
        this.relation = relation;
        this.fightExp = fightExp;
        this.avatar = avatar;
    }

    public int getId() {
        return  this.id;
    }

    public String getName() {
        return  this.name;
    }

    public String getShortInfo() {
        return  this.shortInfo;
    }

    public String getRelation() {
        return  this.relation;
    }

    public String getFightExp() {
        return  this.fightExp;
    }

    public int getAvatar() {
        return  this.avatar;
    }
}
