package com.example.wordle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class language {

    @FXML
    private ImageView bt_pt;

    @FXML
    private ImageView bt_uk;

    @FXML
    private ImageView bt_fr;

    @FXML
    private void click() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pt_lvl1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) bt_pt.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void click_uk() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("uk_lvl1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) bt_uk.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void click_fr() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fr_lvl1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) bt_fr.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void mouse_entered(Event e)
    {
        ImageView whoClicked = (ImageView) e.getSource();
        whoClicked.setStyle("-fx-opacity: 100%;");
    }
    @FXML
    protected void mouse_exit(Event e)
    {
        ImageView whoClicked = (ImageView) e.getSource();
        whoClicked.setStyle("-fx-opacity: 50%;");
    }
}
