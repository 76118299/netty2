package com.netty.custom;

import io.netty.handler.codec.marshalling.*;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/1 0001.
 */
public class MarShallingUtil {
    /**
     * 编码
     * @return
     */
    public static Marshaller getMarshaller() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        UnmarshallerProvider unmarshallerProvider =new DefaultUnmarshallerProvider(marshallerFactory,marshallingConfiguration);
      return  marshallerFactory.createMarshaller(marshallingConfiguration);
       // return new MarshallingDecoder(unmarshallerProvider,1024*1024*1);

    }

    /**
     * 解码
     * @return
     */
    public static Unmarshaller getUnmarshaller() throws IOException {
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
         return marshallerFactory.createUnmarshaller(marshallingConfiguration);

//        MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory,marshallingConfiguration);
//        return new MarshallingEncoder(marshallerProvider);
    }


}
