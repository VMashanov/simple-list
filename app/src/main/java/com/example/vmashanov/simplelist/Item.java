package com.example.vmashanov.simplelist;

public final class Item {
    private long id;
    private String title;
    private String description;
    private long parent;
    private boolean done;

    public Item(long id, String title, String description, long parent, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.parent = parent;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
