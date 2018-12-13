package net.uoit.csci4100.mobiledeviceproject;

import java.util.Date;

/**
 * The ChatMessage class contains a chatMessage constructor and corresponding getter/setter methods.
 */
public class ChatMessage {
    private String messageUser;
    private String messageText;
    private long messageTime;

    /**
     * The chatMessage empty constructor
     */
    public ChatMessage() {
    }

    /**
     * The chatMessage constructor.
     * @param messageUser - User that sent the message. (String)
     * @param messageText - Message content. (String)
     */
    public ChatMessage(String messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = new Date().getTime(); //?
    }

    /**
     * MessageText getter method.
     * @return messageText. (String)
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * MessageText setter method.
     * @param messageText - Message content. (String)
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * MessageUser getter method.
     * @return messageUser - User that sent the message. (String)
     */
    public String getMessageUser() {
        return messageUser;
    }

    /**
     * MessageUser setter method.
     * @param messageUser - User that sent the message. (String)
     */
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    /**
     * MessageTime getter method.
     * @return messageTime - Time message was sent. (long)
     */
    public long getMessageTime() {
        return messageTime;
    }

    /**
     * MessageTime setter method.
     * @param messageTime - Time message was sent. (long)
     */
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
