package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import domain.Main;
import domain.Student;


public class Controller
{
    // Insert tab
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    @FXML
    private void saveStudent()
    {
        String name = nameField.getText();
        if (name.length() == 0)
        {
            AlertBox.display("Update result", "Information not saved.\nPlease enter a name!");
            return;
        }
        String email = emailField.getText();
        if (email.length() == 0)
        {
            email = null;
        }

        if (Main.saveStudentToDB(name, email))
        {
            AlertBox.display("Save result", "Information saved");
        }
    }

    // ShowTable tab
    @FXML
    private TableView<Student> showTable;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    private ChangeListener<Number> selectIndexListener;
    private Student selectedStudent;

    @FXML
    private void loadTable()
    {
        //showTable.setEditable(true);

        ObservableList<Student> data = FXCollections.observableArrayList(Main.getAllStudent());
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

        showTable.setItems(data);

        if (selectIndexListener == null) // The listener wasn't yet created,neither added
        {
            selectIndexListener = new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                {
                    int index = newValue.intValue();
                    if (index >= 0) // If a row is selected and the user switches off from the ShowTable tab
                    // the index returned will be -1
                    {
                        selectedStudent = data.get(index);
                    } else // FIXME: 03-Apr-17
                    {
                        selectedStudent = null;
                    }
                }
            };
            TableView.TableViewSelectionModel<Student> tvSelModel = showTable.getSelectionModel();
            tvSelModel.selectedIndexProperty().addListener(selectIndexListener);
        }
    }

    @FXML
    private void deleteRequest()
    {
        boolean confirmed = ConfirmationBox.confirmBox("Delete", "Are you sure you want to delete selected item?");
        if (confirmed)
        {
            if (Main.deleteStudent(selectedStudent))
            {
                // FIXME: 31-Mar-17 selectedStudent = null;
                loadTable();
            }
        }
    }

    @FXML
    private void editStudent()
    {
        //EditWindow.editStudent(selectedStudent);
        new EditWindow(selectedStudent);
    }


    /*public static void main(String[] args)
    {

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Properties connectionProp = new Properties();
            connectionProp.put("user", "root");
            connectionProp.put("password", "");
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/dat16j", connectionProp);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            while (rs.next())
            {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " +
                    rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/

    /*public boolean executeSQL(String sql)
    {
        try
        {
            Properties connectionProp = new Properties();
            // If connection is not made, you might need to register the driver
            // some driver require this, for others is optional
            // though it never hurts to do it
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connectionProp.put("user", "root");
            connectionProp.put("password", "");
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/mysql", connectionProp);
            // Do something with the Connection
            System.out.println("connected");

            Statement s = conn.createStatement();
            try
            {
                s.executeUpdate("INSERT INTO student VALUES('Edward', 'Sergheiev', \"1959-2-22\", 'dcooper@aol.com', NULL);");
                System.out.println("query executed");
                return true;
            } finally
            {
                s.close(); // always close the statement to prevent memory leak
            }
            // better use a try-finally to ensure it will always be closed,even if we get errors
        } catch (SQLException ex)
        {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }*/
}
