package gui;

import domain.Main;
import domain.Student;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by Chris on 30-Mar-17.
 */

public class EditWindow
{/*

    @FXML
    private TextField nameLabel;
    @FXML
    private TextField emailLabel;
    private Student selectedStudent;

    private EditWindow(Student student)
    {
        selectedStudent = student;
        displayWindow();
    }

    public EditWindow()
    {
    }

    private void displayWindow()
    {
        Parent root;
        try
        {
            Stage window = new Stage();
            root = FXMLLoader.load(getClass().getResource("../gui/editWindow.fxml"));

            String nameLabel = selectedStudent.getName();
            this.nameLabel.setText(nameLabel);
            String emailLabel = selectedStudent.getEmail();
            this.emailLabel.setText(emailLabel);

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Edit");
            //window.setMinWidth(250);

            //Display window and wait for it to be closed before returning
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setSelectedStudent(Student student)
    {
        this.selectedStudent = student;
        displayWindow();
    }

    public static Student editStudent(Student oldStudent)
    {
        //EditWindow editWindow = new EditWindow(oldStudent);
        EditWindow test = new EditWindow();
        test.setSelectedStudent(oldStudent);

        return oldStudent;
    }*/

    private Student selectedStudent;

    public EditWindow(Student selectedStudent)
    {
        //this.selectedStudent = selectedStudent;
        if(selectedStudent != null)
        {
            display(selectedStudent);
        }else
        {
            AlertBox.display("Invalid student", "Choose an element first");
        }
    }

    private void display(Student student)
    {
        AnchorPane root = new AnchorPane();
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit");

        Label nameLabel = new Label("Name");
        Label emailLabel = new Label("Email");

        TextField nameField = new TextField();
        TextField emailField = new TextField();

        nameLabel.setLayoutX(84);
        nameLabel.setLayoutY(67);

        emailLabel.setLayoutX(84);
        emailLabel.setLayoutY(124);

        nameField.setLayoutX(173);
        nameField.setLayoutY(61);

        emailField.setLayoutX(173);
        emailField.setLayoutY(118);

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction((ActionEvent e) -> {


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

            Student newStudent = selectedStudent.copy();
            newStudent.setName(name);
            newStudent.setEmail(email);

            if(Main.updateStudent(newStudent))
            {
                System.out.println("Information saved.");
                System.out.println("display this in a window");
                stage.close();
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e ->
        {
            //if (!nameField.getText().equals(selectedStudent.getName()) || // if name text is different than actual saved name
            //                !emailField.getText().equals(selectedStudent.getEmail())) // if email text is different than actual saved emailLabel
            //{
                if(ConfirmationBox.confirmBox("Cancel changes", "The changes will not be saved."))
                {
                    stage.close();
                }
//            }else // information is the same
//            {
//                stage.close();
//            }
        });

        saveBtn.setLayoutX(49);
        saveBtn.setLayoutY(318);

        cancelBtn.setLayoutX(446);
        cancelBtn.setLayoutY(318);

        nameField.setText(selectedStudent.getName());
        emailField.setText(selectedStudent.getEmail());

        root.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, saveBtn, cancelBtn);

        stage.setScene(new Scene(root, 600, 350));

        stage.showAndWait();
    }
}
