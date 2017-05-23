package com.lovehunterx.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.lovehunterx.LoveHunterX;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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
import io.netty.util.CharsetUtil;

import static com.lovehunterx.LoveHunterX.connection;

/**
 * Created by Hans on 5/20/2017.
 */

public class Connection {
    private Channel channel;
    private static final String HOST = "144.217.84.58";
    private static final int PORT = 8080;

    public void init() throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ClientChannelInitializer());
            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();
            channel = f.channel();

            // Wait until the connection is closed.
            //f.channel().closeFuture();

        } finally {
            //workerGroup.shutdownGracefully();
        }
    }
    public Channel getChannel() {
        return this.channel;
    }

    public boolean send(Packet p){
        if(channel == null || !channel.isActive()) {
            return false;
        }

        channel.writeAndFlush(p.toJSON());
        return true;
    }



    private class Handler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String m = (String) msg;
            //String message = m.toString(CharsetUtil.US_ASCII);
            //Gdx.app.log("response", m);
            Json json = new Json();
            Packet p = json.fromJson(Packet.class, m);
            //Gdx.app.log("test", p.getAction());
            interpretPacket(p);
        }

        private void interpretPacket(Packet p) {
            if(p.getAction().equals("auth")) {
                handleAuthentication(p);
            } else {
                if(p.getAction().equals("reg")) {
                    handleRegistration(p);
                }
            }
        }

        private void handleAuthentication(Packet p) {
            String message = (Boolean.parseBoolean(p.getData("success"))) ? "Log in worked dude what's good" : "log in failed goofy";
            LoveHunterX.ls.showMessage(message);
        }
        private void handleRegistration(Packet p) {
            String message = (Boolean.parseBoolean(p.getData("success"))) ? "Registration worked dude log in now" : "you goofy this account already exists";
            LoveHunterX.ls.showMessage(message);
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