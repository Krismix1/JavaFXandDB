package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Chris on 27-Mar-17.
 */
public class ConfirmationBox
{
    public static boolean confirmBox(String title, String message)
    {
        /*Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            System.out.println("yes");window.close();});

        Button noButton = new Button("No");
        noButton.setOnAction(event -> {
            System.out.println("no");window.close();});

        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(yesButton, noButton);
        buttonLayout.setAlignment(Pos.CENTER);

        layout.getChildren().add(buttonLayout);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            return true;
        } else {
            // ... user chose CANCEL or closed the dialog
            return false;
        }
    }
}
