package mainApp;
import com.jfoenix.controls.JFXTextField;
import data.User;
import javafx.fxml.FXML;
import mainApp.App;
public class Controller_Dashboard
{
    @FXML
    public JFXTextField firstname, lastname, emailid, phone;
    public void initialize()
    {
        firstname.setText(App.user.getFirstName());
        lastname.setText((App.user.getLastName()));
        emailid.setText(App.user.getEmail());
        phone.setText(App.user.getPhone());
    }
    public void onshareclicked()
    {

    }
    public void onsearchclicked()
    {

    }
}
