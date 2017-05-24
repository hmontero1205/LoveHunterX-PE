package com.lovehunterx.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Connection {
    private static final String HOST = "144.217.84.58";
    private static final int PORT = 8080;
    private Channel channel;

    private HashMap<String, ArrayList<Listener>> listeners;

    public void init() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ClientChannelInitializer());

        listeners = new HashMap<String, ArrayList<Listener>>();

        // Start the client.
        ChannelFuture f = b.connect(HOST, PORT).sync();
        channel = f.channel();
    }

    public void end() {
        if (channel != null || !channel.isActive()) {
            return;
        }

        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return this.channel;
    }

    public boolean send(Packet p) {
        if (channel == null || !channel.isActive()) {
            return false;
        }

        channel.writeAndFlush(p.toJSON());
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

    private class Handler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String m = (String) msg;
            Json json = new Json();
            Packet p = json.fromJson(Packet.class, m);
            interpretPacket(p);
        }

        private void interpretPacket(final Packet p) {
            final ArrayList<Listener> responders = listeners.get(p.getAction());
            if (responders == null) {
                return;
            }

            for (final Listener l : responders)
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        l.handle(p);
                    }
                });
        }
    }

    private class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("handler", new Handler());
        }
    }
}