package com.lovehunterx.networking;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;

/**
 * Created by Hans on 5/21/2017.
 */

public class Packet {
    private String action;
    private HashMap<String, String> data;

    public Packet(String a, HashMap<String, String> d) {
        this.action = a;
        this.data = d;
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


}
