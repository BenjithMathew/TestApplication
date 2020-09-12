package com.android.car.testapplication.Models;

public class EmpSub {
    private int id;
    private String name;
    private String image;
    private String cmp_name;

    public EmpSub() {
    }

    public EmpSub(int id, String name, String image, String cmp_name) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.cmp_name = cmp_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCmp_name() {
        return cmp_name;
    }

    public void setCmp_name(String cmp_name) {
        this.cmp_name = cmp_name;
    }

    @Override
    public String toString() {
        return "EmpSub{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", cmp_name='" + cmp_name + '\'' +
                '}';
    }
}
