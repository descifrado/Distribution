package mainClasses;

import java.sql.Connection;

public class Main {
    static String user="root";
    static String password="root";
    static String host="jdbc:mysql://localhost:3306/Distribution";
    static Connection connection=MysqlConnection.connect();
    public static void main(String[] args) {

    }

}
