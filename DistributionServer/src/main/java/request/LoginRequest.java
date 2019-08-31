package request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String emailId , password ;
    public LoginRequest(String emailId,String password){
        this.emailId = emailId;
        this.password = password;
    }
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
