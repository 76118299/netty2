package com.netty.custom;


import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.Unmarshaller;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/4 0004.
 */
public class MarshallingEncoder {
    //空白位置 预留BODY的长度
    private static final byte [] LENGTH_PLACEHOLEDR = new byte[4];
    private Marshaller marshaller;
    public MarshallingEncoder()  {
        try {
            marshaller = MarShallingUtil.getMarshaller();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void encoder(Object body, ByteBuf sendBuf) throws IOException {
        try {
            //数据的其实位置
            int pos = sendBuf.writerIndex();
            //站位操作
            sendBuf.readBytes(LENGTH_PLACEHOLEDR);
            //写数据
            ChannelBufferByteOutput byteout = new ChannelBufferByteOutput(sendBuf);

            marshaller.start(byteout);
            marshaller.writeObject(body);
            marshaller.flush();
            //总长度-起始位子-预留长度
            //把body的长度存入预留的位置
            sendBuf.setInt(pos, sendBuf.writerIndex() - pos - 4);
            //sendBuf.writerIndex();
        }finally {
            marshaller.close();
        }
    }
}
