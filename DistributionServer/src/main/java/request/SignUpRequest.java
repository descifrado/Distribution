package request;

import data.User;

import java.io.Serializable;

public class SignUpRequest implements Serializable
{
    private String password;
    private User user;
    public SignUpRequest(String password, User user)
    {
        this.password=password;
        this.user=user;
    }
    public String getPassword()
    {
        return password;
    }
    public User getUser() throws CloneNotSupportedException
    {
        return (User) this.user.clone();
    }
}
