package org.martin.inventory.model;

public class Message {
    private String author;
    private String content;
    private long timestamp;

    public Message() { }

    public Message(String author, String content, long timestamp) {

        if(!author.isEmpty()) this.author = author;
        else
        {
            throw new java.lang.IllegalArgumentException("Message class object cannot be initialized with an empty author value");
        }
        if(!content.isEmpty()) this.content = content;
        else
        {
            throw new java.lang.IllegalArgumentException("Message class object cannot be initialized with an empty message content value");
        }
        this.timestamp = timestamp;
    }

    public String getAuthor() { return author; }

    public boolean setAuthor(String author) {
        if (author.isEmpty()) return false;
        this.author = author;
        return true;
    }

    public String getContent() { return content; }

    public boolean setContent(String content) {
        if (content.isEmpty()) return false;
        this.content = content;
        return true;
    }

    public long getTimestamp() { return timestamp; }

    public boolean setTimestamp(long timestamp) {
        if (timestamp < 0) return false;
        this.timestamp = timestamp;
        return true;
    }
}
