package com.netty.custom;

/**
 * Created by Administrator on 2018/3/4 0004.
 */
public class Head {
    public int getCcode() {
        return ccode;
    }

    public void setCcode(int ccode) {
        this.ccode = ccode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    private int ccode=1121;  //消息的唯一标示
    private int length ;//消息的总长度
    private long sessionId; //回话ID
    private byte type;  //消息的类型
    private byte priority;//消息的优先级别

}
