package data;
/**
 *  Serializable class for object sharing
 */

import java.io.Serializable;

public class User implements Serializable,Cloneable
{
    /**
     *  Variables
     */
    private String firstName, lastName, email, phone, userUID;

    /**
     *
     * @return First Name of User
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName First Name of User
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return Last Name of User
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName Last Name of User
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return Email of the User(Unique)
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email Email of the User
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return Phone Number of the User
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone Phone Number of The User
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return User UID of the User Unique Identifier
     */
    public String getUserUID() {
        return userUID;
    }

    /**
     *
     * @param userUID User UID of the User Unique Identifier
     */
    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    /**
     *
     * @return Clonable Object
     * @throws CloneNotSupportedException Exception Handling
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
