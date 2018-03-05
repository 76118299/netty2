package com.netty.custom;

import com.netty.entity.PersonFactory;
import com.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b =new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new Decoder(1024 * 1024 * 5, 4, 4))
                                .addLast(new Encoder())
                        .addLast(new clientHandler());


                    }
                });
        ChannelFuture cf = b.connect("127.0.0.1", 8765).syncUninterruptibly();
        Channel channel = cf.channel();
        for(int i=0;i<10;i++){
            Message m = new Message();
            channel.writeAndFlush(m);
        }


        //释放连接
        cf.channel().closeFuture().sync();
        group.shutdownGracefully();

    }
}
