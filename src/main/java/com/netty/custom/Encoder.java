package com.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;


/**
 * Created by Administrator on 2018/3/4 0004.
 */
public class Encoder extends MessageToByteEncoder <Message>{
    private MarshallingEncoder marshallingEncoder;

        public Encoder( )  {


                this.marshallingEncoder =new  MarshallingEncoder();

        }

    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf sendBuf) throws Exception {
            if(null==message||null==message.getHead()){
                throw new Exception("消息为NULL");
            }
            //消息头
          sendBuf.writeInt(message.getHead().getCcode());
          sendBuf.writeInt(message.getHead().getLength());
          sendBuf.writeLong(message.getHead().getSessionId());
          sendBuf.writeByte(message.getHead().getType());
          sendBuf.writeByte(message.getHead().getPriority());
          Object body = message.getBody();
          if(body!=null){
              marshallingEncoder.encoder(body,sendBuf);
          }else {
              //body为null进行补位操作
              sendBuf.writeInt(0);
          }

        //设置总长度
         sendBuf.setInt(4,sendBuf.readableBytes()-8);

    }
}
