package com.example.bloodbank;

public class Donetorsmodal {
    String id,name,type,img,address,phone;

    public Donetorsmodal(String id, String name, String type, String img, String address, String phone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.img = img;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
