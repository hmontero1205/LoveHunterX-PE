package com.lovehunterx.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.lovehunterx.LoveHunterX;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SocketUtils;

public class Connection {
    //Kevin's: 144.217.84.58
    //Hans's local: 255.255.255.255

    private static final String HOST = "144.217.84.58";
    private static final int PORT = 8080;
    private static final InetSocketAddress SERVER_ADDRESS = SocketUtils.socketAddress(HOST, PORT);
    private Channel channel;

    private HashMap<String, ArrayList<Listener>> listeners;

    public void init() throws InterruptedException {
        listeners = new HashMap<String, ArrayList<Listener>>();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
         .option(ChannelOption.SO_BROADCAST, true)
         .channel(NioDatagramChannel.class)
         .handler(new Handler());

        channel = b.bind(0).sync().channel();
        System.out.println(channel.remoteAddress());
    }

    public void end() {
        if (channel == null) {
            return;
        }

        Packet dc = Packet.createDisconnectPacket();
        send(dc);

        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean send(Packet p) {
        if (channel == null) {
            return false;
        }

        ByteBuf buf = Unpooled.copiedBuffer(p.toJSON(), CharsetUtil.UTF_8);

        try {
            channel.writeAndFlush(new DatagramPacket(buf, SERVER_ADDRESS)).sync();
        } catch (Exception e) {
            LoveHunterX.getCurrentScreen().displayNotification("Connected failed u goofy >;(");
        }
        return true;
    }

    public void registerListener(String name, Listener listener) {
        if (listeners.get(name) != null) {
            listeners.get(name).add(listener);
        } else {
            listeners.put(name, new ArrayList<Listener>(Arrays.asList(listener)));
        }
    }

    public void clearListeners() {
        listeners.clear();
    }

    private class Handler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
            String m = (String) packet.content().toString(CharsetUtil.US_ASCII);
            Json json = new Json();
            Packet p = json.fromJson(Packet.class, m);
            interpretPacket(p);
        }

        private void interpretPacket(final Packet p) {
            ArrayList<Listener> responders = listeners.get(p.getAction());
            if (responders == null) {
                return;
            }

            for (final Listener l : responders) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        l.handle(p);
                    }
                });
            }
        }
    }
}