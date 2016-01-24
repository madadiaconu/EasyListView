package com.madadiaconu.easylistview.bl;

/**
 * Created by Diaconu Madalina on 23.01.2016.
 */
public class Widget {

    private int idInt;
    private String id;
    private String type;

    public Widget(int idInt, String id, String type) {
        this.idInt = idInt;
        this.id = id;
        this.type = type;
    }

    public int getIdInt() {
        return idInt;
    }

    public void setIdInt(int idInt) {
        this.idInt = idInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "idInt=" + idInt +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
