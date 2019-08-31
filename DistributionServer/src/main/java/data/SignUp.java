package data;

import request.SignUpRequest;

import java.io.Serializable;

public class SignUp implements Serializable
{
    private String uid, password;
    private String email, phone;
    private String fname, lname;

    public SignUp(SignUpRequest user)
    {
        this.fname=user.getFirsNname();
        this.lname=user.getLastName();
        this.phone=user.getPhone();
        this.email=user.getEmail();
    }
}
