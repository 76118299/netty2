package com.netty.custom;

import com.netty.entity.PersonFactory;
import com.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2018/3/5 0005.
 */
public class myServer {
    private int port;

    public myServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        //接收客户端连接的组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //接收读写操作的线程组
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //服务端配置启动项
        ServerBootstrap sbs = new ServerBootstrap().group(bossGroup, workGroup)
                //设置NIO模式
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                //初始化绑定通道
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline p = socketChannel.pipeline();
                        p.addLast(new Decoder(1024 * 1024 * 5, 4, 4))
                                .addLast(new Encoder())
                        .addLast(new myHandler());


                    }
                })

                .option(ChannelOption.SO_BACKLOG, 1024) //设置TCP 缓冲区
                .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)//设置发送数据的缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)//设置接收数据的缓冲大小
                .childOption(ChannelOption.SO_KEEPALIVE, true);  //设置长连接
        //同步等待
        ChannelFuture cf = sbs.bind(port).sync();
        System.out.print("Server Start listen at " + port);
        //等待关闭连接
        cf.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();

    }

}
