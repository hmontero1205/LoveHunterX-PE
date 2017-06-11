package com.lovehunterx.networking;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.lovehunterx.game.entities.Furniture;
import com.lovehunterx.screens.RoomScreen;

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

    public static Packet createChooseSpritePacket(String user, String sprite){
        HashMap<String, String> regData = new HashMap<String, String>();
        regData.put("user",user);
        regData.put("sprite",sprite);
        return new Packet("choose_sprite",regData);
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

    public static Packet createFurniturePacket(Furniture f) {
        HashMap<String, String> furData = new HashMap<String, String>();
        furData.put("type", f.getType());
        furData.put("uid", String.valueOf(f.getUniqueId()));
        furData.put("x", String.valueOf(f.getX()));
        furData.put("y", String.valueOf(f.getY()));
        return new Packet("update_furniture", furData);
    }

    public static Packet createRemoveFurniturePacket(Furniture f) {
        HashMap<String, String> furData = new HashMap<String, String>();
        furData.put("type", f.getType());
        furData.put("uid", String.valueOf(f.getUniqueId()));
        return new Packet("remove_furniture", furData);
    }

    public static Packet createMovementPacket(float velX, float velY) {
        HashMap<String, String> moveData = new HashMap<String, String>();
        moveData.put("vel_x", String.valueOf(velX));
        moveData.put("vel_y", String.valueOf(velY));
        return new Packet("move", moveData);
    }

    public static Packet createChatPacket(String message) {
        HashMap<String, String> chatData = new HashMap<String, String>();
        chatData.put("message", message);
        return new Packet("chat", chatData);
    }

    public static Packet createJoinRoomPacket(String room) {
        HashMap<String, String> joinData = new HashMap<String, String>();
        joinData.put("room", room);
        return new Packet("join", joinData);
    }

    public static Packet createDisconnectPacket() {
        return new Packet("disconnect", new HashMap<String, String>());
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

    public static Packet createGetMoneyPacket(String username) {
        HashMap<String, String> moneyData = new HashMap<String, String>();
        moneyData.put("user", username);
        return new Packet("get_money", moneyData);
    }

    public static Packet createPurchasePacket(String username, Double money, String type) {
        HashMap<String, String> moneyData = new HashMap<String, String>();
        moneyData.put("user", username);
        moneyData.put("money", Double.toString(money));
        moneyData.put("type", type);
        return new Packet("purchase", moneyData);
    }
}
