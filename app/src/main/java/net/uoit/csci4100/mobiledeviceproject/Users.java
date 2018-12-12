package net.uoit.csci4100.mobiledeviceproject;

import android.media.Image;

public class Users {
    private String name;
    private String email;
    private String image;

    Users(){

    }

    Users(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String userImage) {
        this.image = userImage;
    }
}
