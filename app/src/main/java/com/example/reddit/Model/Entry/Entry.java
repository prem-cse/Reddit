package com.example.reddit.Model.Entry;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "entry", strict = false) // to pick only selected items
public class Entry implements Serializable {

    @Element(name = "content")
    String content;
    @Element(required = false, name = "author")
    Author author;
    @Element(name = "id")
    String id;
    @Element(name = "title")
    String title;
    @Element(name = "updated")
    String updated;

    public Entry(){}

    public Entry(String content, Author author, String id, String title, String updated) {
        this.content = content;
        this.author = author;
        this.id = id;
        this.title = title;
        this.updated = updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "content='" + content + '\'' + "\n" +
                ", author='" + author + '\'' + "\n" +
                ", id='" + id + '\'' + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", updated='" + updated + '\'' + "\n" +
                '}';
    }
}
