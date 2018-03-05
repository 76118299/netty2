package com.netty.custom;

/**
 * Created by Administrator on 2018/3/4 0004.
 */
public class Message {
    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Object getBody() {
        return Body;
    }

    public void setBody(Object body) {
        Body = body;
    }

    private Head head;
    private Object Body;
}
