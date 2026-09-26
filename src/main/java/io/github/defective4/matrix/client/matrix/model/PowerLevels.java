package io.github.defective4.matrix.client.matrix.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class PowerLevels {
    private Integer ban;
    @SerializedName("events_default")
    private Integer defaultEvents;
    @SerializedName("state_default")
    private Integer defaultState;
    @SerializedName("users_default")
    private Integer defaultUsers;
    private Map<String, Integer> events;
    private Integer historical;
    private Integer invite;
    private Integer kick;
    private Integer redact;
    private Integer room;
    private Map<String, Integer> users;

    public Integer getBan() {
        return ban;
    }

    public Integer getDefaultEvents() {
        return defaultEvents;
    }

    public Integer getDefaultState() {
        return defaultState;
    }

    public Integer getDefaultUsers() {
        return defaultUsers;
    }

    public Map<String, Integer> getEvents() {
        return events;
    }

    public Integer getHistorical() {
        return historical;
    }

    public Integer getInvite() {
        return invite;
    }

    public Integer getKick() {
        return kick;
    }

    public Integer getRedact() {
        return redact;
    }

    public Integer getRoom() {
        return room;
    }

    public Map<String, Integer> getUsers() {
        return users;
    }

    public PowerLevels setBan(Integer ban) {
        this.ban = ban;
        return this;
    }

    public PowerLevels setDefaultEvents(Integer defaultEvents) {
        this.defaultEvents = defaultEvents;
        return this;
    }

    public PowerLevels setDefaultState(Integer defaultState) {
        this.defaultState = defaultState;
        return this;
    }

    public PowerLevels setDefaultUsers(Integer defaultUsers) {
        this.defaultUsers = defaultUsers;
        return this;
    }

    public PowerLevels setEvents(Map<String, Integer> events) {
        this.events = events;
        return this;
    }

    public PowerLevels setHistorical(Integer historical) {
        this.historical = historical;
        return this;
    }

    public PowerLevels setInvite(Integer invite) {
        this.invite = invite;
        return this;
    }

    public PowerLevels setKick(Integer kick) {
        this.kick = kick;
        return this;
    }

    public PowerLevels setRedact(Integer redact) {
        this.redact = redact;
        return this;
    }

    public PowerLevels setRoom(Integer room) {
        this.room = room;
        return this;
    }

    public PowerLevels setUsers(Map<String, Integer> users) {
        this.users = users;
        return this;
    }

}
