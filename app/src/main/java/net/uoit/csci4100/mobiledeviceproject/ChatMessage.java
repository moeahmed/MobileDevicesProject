package net.uoit.csci4100.mobiledeviceproject;

import java.util.Date;

public class ChatMessage {
    private String messageUser;
    private String messageText;
    private long messageTime;

    public ChatMessage() {
    }

    public ChatMessage(String messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = new Date().getTime(); //?
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
