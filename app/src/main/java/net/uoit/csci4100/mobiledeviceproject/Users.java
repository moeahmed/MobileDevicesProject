package net.uoit.csci4100.mobiledeviceproject;

import android.media.Image;

public class Users {
    private String name;
    private String email;
    private Image userImage;

    Users(String name, String email, Image image) {
        this.name = name;
        this.email = email;
        this.userImage = image;
    }

    public String getName() {
        return name;
    }

    public Image getUserImage() {
        return userImage;
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

    public void setUserImage(Image userImage) {
        this.userImage = userImage;
    }
}
