package data;

import java.io.Serializable;

public class User implements Serializable
{
    private String firstName, lastName, email, phone, userUID;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserUID() {
        return uid;
    }

    public void setUserUID(String uid) {
        this.uid = uid;
    }
}
