package com.example.demo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("l.fxml")));
        stage.setTitle("Dictionary");
        stage.setWidth(748);
        stage.setHeight(506);
        stage.setResizable(false);
        Scene scene = new Scene(root,800,400);
        String css = Objects.requireNonNull(this.getClass().getResource("test.css")).toExternalForm();
        scene.getStylesheets().add(css);
          stage.setScene(scene);
       stage.show();
    }


    }

