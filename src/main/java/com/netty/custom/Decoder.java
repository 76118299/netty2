package com.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;
import java.util.List;

/**
 * LengthFieldBasedFrameDecoder:就是解决拆包和粘包的问题 。
 * Created by Administrator on 2018/3/4 0004.
 */
public class Decoder extends LengthFieldBasedFrameDecoder {
    private   MarShallingDecoder marShallingDecoder;



    /**
     *
     * @param maxFrameLength      :数据的最大长度
     * @param lengthFieldOffset   :长度的偏移量(长度属性的位置)
     * @param lengthFieldLength   :长度属性的长度
     */
    public Decoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marShallingDecoder=new MarShallingDecoder();
    }

    /**
     * 解码
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);

        if(null==frame){
            return null;
        }
        Head head = new Head();
        head.setCcode(frame.readInt());
        head.setLength(frame.readInt());
        head.setSessionId(frame.readLong());
        head.setType(frame.readByte());
        head.setPriority(frame.readByte());
        Message message = new Message();
        message.setHead(head);

        if(frame.readableBytes()>4){
            message.setBody(marShallingDecoder.decoder(in));
        }
        return message;
    }
}
