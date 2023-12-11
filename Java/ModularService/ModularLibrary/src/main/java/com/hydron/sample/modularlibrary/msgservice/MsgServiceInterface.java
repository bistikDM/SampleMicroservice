package com.hydron.sample.modularlibrary.msgservice;

public interface MsgServiceInterface {
    void sendMessage(String topic, Object msg);
}
