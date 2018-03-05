package com.netty.custom;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/4 0004.
 */
public class MarShallingDecoder {
    private  Unmarshaller unmarshaller;
    public MarShallingDecoder(){
        try {
             unmarshaller = MarShallingUtil.getUnmarshaller();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Object decoder(ByteBuf in) {
        int bodySize = in.readInt();
        ByteBuf bodyBuf = in.setLong(in.readerIndex(), bodySize);
        ChannelBufferByteInput cin = new ChannelBufferByteInput(bodyBuf);
        try {
            unmarshaller.start(cin);
            Object object = unmarshaller.readObject();
            unmarshaller.finish();
            //读取完毕更新当前的起始位子
            in.readerIndex(in.readerIndex()+bodySize);
            return object;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                unmarshaller.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
