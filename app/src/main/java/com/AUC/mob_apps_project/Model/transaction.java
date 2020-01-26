package com.AUC.mob_apps_project.Model;

public class transaction {
    private String client_name, table;
    private int time;
    private float ammount;

    public transaction() {
    }

    public transaction(String client_name, String table, int time, float ammount) {
        this.client_name = client_name;
        this.table = table;
        this.time = time;
        this.ammount = ammount;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }
}
