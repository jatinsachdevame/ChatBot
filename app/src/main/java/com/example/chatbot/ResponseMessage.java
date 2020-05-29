package com.example.chatbot;

public class ResponseMessage {
    String message;
    boolean isMe;

    public ResponseMessage(String message, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMe() {
        return isMe;
    }
}
