package com.example.reddit.Comments;

public class Comment {
    private String author;
    private String id;
    private String updated;
    private String comment;


    public Comment(){}

    public Comment(String author, String id, String updated, String comment) {
        this.author = author;
        this.id = id;
        this.updated = updated;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", updated='" + updated + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
