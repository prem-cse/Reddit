package com.example.reddit.Model;

import com.example.reddit.Model.Entry.Entry;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.io.Serializable;
import java.util.List;


@Root(name = "feed", strict = false) // to pick only selected items
public class Feed implements Serializable {
    @Element(name = "icon")
    String icon;
    @Element(name = "id")
    String id;
    @Element(name = "logo")
    String logo;
    @Element(name = "title")
    String title;
    @Element(name = "updated")
    String updated;
    @Element(name = "subtitle")
    String subtitle;
    @ElementList(inline = true,name = "entry")
    private List<Entry> entrys;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<Entry> getEntry() {
        return entrys;
    }

    public void setEntry(List<Entry> entry) {
        this.entrys = entry;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "icon='" + icon + '\'' + "\n" +
                ", id='" + id + '\'' + "\n" +
                ", logo='" + logo + '\'' + "\n" +
                ", title='" + title + '\'' + "\n" +
                ", updated='" + updated + '\'' + "\n" +
                ", subtitle='" + subtitle + '\'' + "\n" +
                ", entrys=" + entrys + "\n" +
                '}';
    }
}
