package domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import technicalservices.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application
{
    private static Map<Integer, Student> students = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/mainScene.fxml"));
        primaryStage.setTitle("Dat16j");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public static boolean saveStudentToDB(String... values)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            String email = values[1];
            if(email != null) // length > 0 shouldn't be needed, as we checked when sending the information here
            {
                stmt.executeUpdate("INSERT INTO student values (NULL, '" + values[0] + "', '" + email + "');");
            }else
            {
                stmt.executeUpdate("INSERT INTO student values (NULL, '" + values[0] + "', NULL );");
            }
            // TODO: 02-Apr-17 Decide with null email
            /*java.sql.PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM users WHERE USERNAME = ? AND ROOM = ?");
            stmt.setString(1, username);
            stmt.setInt(2, roomNumber);
            stmt.executeQuery();*/
            con.close();
            return true;
        } catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    private static void readStudentTable()
    {
        try
        {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            while (rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                // FIXME: 02-Apr-17
                /*if (email.equalsIgnoreCase("null"))
                {
                    email = "";
                }*/
                students.put(id, new Student(name, email, id));
            }
            con.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static Collection<Student> getAllStudent()
    {
        readStudentTable();
        return students.values();
    }

    public static boolean deleteStudent(Student student)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM `student` WHERE student_id = " + student.getStudent_id());// FIXME: 30-Mar-17
            students.remove(student.getStudent_id());
            con.close();
            return true;
        } catch (SQLException e)
        {
            System.out.println(e);
            return false;
        }
    }

    public static boolean updateStudent(Student student)
    {
        try
        {
            String newName = student.getName();
            String newEmail = student.getEmail();
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            if(newEmail != null) // TODO: 03-Apr-17 Check email string for length > 0 
            {
                stmt.executeUpdate("UPDATE `student` SET `name`='" + newName + "',`email`='" + newEmail + "' WHERE student_id = " + student.getStudent_id());// FIXME: 30-Mar-17
            }else
            {
                stmt.executeUpdate("UPDATE `student` SET `name`='" + newName + "',`email`= NULL WHERE student_id = " + student.getStudent_id());
            }
            con.close();
            Student oldStudent = students.get(student.getStudent_id());
            // FIXME: 02-Apr-17 if(oldStudent != null)
            oldStudent.setName(newName);
            oldStudent.setEmail(newEmail);
            students.remove(student.getStudent_id());
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }
}
