package com.lovehunterx.networking;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;

public class Packet {
    private String action;
    private HashMap<String, String> data;

    public Packet(String a, HashMap<String, String> d) {
        this.action = a;
        this.data = d;
    }

    public Packet(String a) {
        this.action = a;
        this.data = new HashMap<String, String>();
    }

    public Packet() {
    }

    public static Packet createAuthPacket(String user, String pass) {
        HashMap<String, String> authData = new HashMap<String, String>();
        authData.put("user", user);
        authData.put("pass", pass);
        return new Packet("auth", authData);
    }

    public static Packet createRegPacket(String user, String pass) {
        HashMap<String, String> regData = new HashMap<String, String>();
        regData.put("user", user);
        regData.put("pass", pass);
        return new Packet("reg", regData);
    }

    public static Packet createMovementPacket(int dir) {
        HashMap<String, String> moveData = new HashMap<String, String>();
        moveData.put("direction", String.valueOf(dir));
        return new Packet("move", moveData);
    }

    public static Packet createJoinRoomPacket(String room) {
        HashMap<String, String> joinData = new HashMap<String, String>();
        joinData.put("room", room);
        return new Packet("join", joinData);
    }

    public String toJSON() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        return json.toJson(this, Packet.class);
    }

    public String getAction() {
        return this.action;
    }

    public String getData(String key) {
        return this.data.get(key);
    }

    public void addData(String key, String val) {
        this.data.put(key, val);
    }

}
