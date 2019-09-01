package authenticationHandler;

import mainClasses.Main;
import request.Response;
import request.SignUpRequest;
import tools.UIDGenerator;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp implements Serializable
{
    private String userUID, password;
    private String email, phone;
    private String fname, lname;

    public SignUp(SignUpRequest request) throws CloneNotSupportedException {
        this.fname=request.getUser().getFirstName();
        this.lname=request.getUser().getLastName();
        this.phone=request.getUser().getPhone();
        this.email=request.getUser().getEmail();
        this.password=request.getPassword();
        this.userUID=request.getUser().getUserUID();
    }

    public Response insert()
    {
        String q="INSERT INTO User VALUES (?,?,?,?,?);";
        try
        {
            PreparedStatement stmt= Main.connection.prepareStatement(q);
            stmt.setString(1,this.userUID);
            stmt.setString(2,this.fname);
            stmt.setString(3,this.lname);
            stmt.setString(4,this.email);
            stmt.setString(5,this.phone);
            stmt.executeUpdate();
            return new Response((UIDGenerator.generateuid()),null,true);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new Response(UIDGenerator.generateuid(),null,false);
    }
}
