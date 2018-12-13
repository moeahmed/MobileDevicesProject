package net.uoit.csci4100.mobiledeviceproject;

public class Messages {
    private String from;
    private String message;
    private String datatype;

    public Messages() {

    }

    public Messages(String from, String message, String datatype) {
        this.from = from;
        this.message = message;
        this.datatype = datatype;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
