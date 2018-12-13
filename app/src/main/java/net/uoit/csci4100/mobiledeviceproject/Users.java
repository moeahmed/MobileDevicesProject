package net.uoit.csci4100.mobiledeviceproject;

import android.media.Image;

/**
 * The User class.
 */
public class Users {
    private String name;
    private String email;
    private String image;

    /**
     * The empty User constructor.
     */
    public Users(){

    }

    /**
     * The Users constructor.
     * @param name - User name. (String)
     * @param email - User email. (String)
     * @param image - User profile image. (String)
     */
    public Users(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    /**
     * User name getter.
     * @return name - User name. (String)
     */
    public String getName() {
        return name;
    }

    /**
     * User profile image getter.
     * @return image - User profile image. (String)
     */
    public String getImage() {
        return image;
    }

    /**
     * User email getter.
     * @return email - User email. (String)
     */
    public String getEmail() {
        return email;
    }

    /**
     * User name setter.
     * @param name - User name. (String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * User email setter.
     * @param email - User email. (String)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * User email setter.
     * @param userImage - User profile image. (String)
     */
    public void setImage(String userImage) {
        this.image = userImage;
    }
}
