package com.example.reddit.Model.Entry;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(name = "author",strict = false)
public class Author implements Serializable {

    @Element(name = "name")
    String name;
    @Element(name = "uri")
    String uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' + "\n" +
                ", uri='" + uri + '\'' + "\n" +
                '}';
    }
}
