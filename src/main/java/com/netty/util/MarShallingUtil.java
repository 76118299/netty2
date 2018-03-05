package com.netty.util;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * Created by Administrator on 2018/3/1 0001.
 */
public class MarShallingUtil {
    /**
     * 解码
     * @return
     */
    public static MarshallingDecoder getMarshallingDecoder(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        UnmarshallerProvider unmarshallerProvider =new DefaultUnmarshallerProvider(marshallerFactory,marshallingConfiguration);
        return new MarshallingDecoder(unmarshallerProvider,1024*1024*1);

    }

    /**
     * 编码
     * @return
     */
    public static MarshallingEncoder getMarshallingEncoder(){
        MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
        marshallingConfiguration.setVersion(5);
        MarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory,marshallingConfiguration);
        return new MarshallingEncoder(marshallerProvider);
    }


}
