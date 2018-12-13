package net.uoit.csci4100.mobiledeviceproject;

/**
 * The Messages class.
 */
public class Messages {
    private String from;
    private String message;
    private String datatype;

    /**
     * Empty Messages constructor.
     */
    public Messages() {

    }

    /**
     * Messages constructor.
     * @param from - Message author. (String)
     * @param message - Message content. (String)
     * @param datatype - Message datatype. (String)
     */
    public Messages(String from, String message, String datatype) {
        this.from = from;
        this.message = message;
        this.datatype = datatype;
    }

    /**
     * Message author getter.
     * @return from - Message author. (String)
     */
    public String getFrom() {
        return from;
    }

    /**
     * Message author setter.
     * @param from - Message author. (String)
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Message content getter.
     * @return message - Message content. (String)
     */
    public String getMessage() {
        return message;
    }

    /**
     * Message content setter.
     * @param message - Message content. (String)
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Message datatype getter.
     * @return datatype - Message datatype. (String)
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Message datatype setter.
     * @return datatype - Message datatype. (String)
     */
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
}
