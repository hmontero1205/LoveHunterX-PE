package com.lovehunterx.networking;

import com.badlogic.gdx.Gdx;

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

/**
 * Created by Hans on 5/20/2017.
 */

public class Connection {
    private Channel channel;
    private static final String HOST = "98.113.92.111";
    private static final int PORT = 8080;

    public Connection() throws Exception {
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


    private class Handler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            String msg = "OH SHIT!!";
            ctx.writeAndFlush(msg);
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
