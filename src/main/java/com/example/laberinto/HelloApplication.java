package com.example.laberinto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}

// s
// istema de planeacion urbana
// Sistema para sistema de DRO
// Libro de puño y letra --> nomas aplica para eso
// Control de los panteones
// 8 reinas que no se coman en un tablero
// IDE delfi

//DS18B20
//ELECTRO VALVULA
//OXIGENACION
//TUVIDES DEL AGUA
//DO6400 OXIGENACION
//COMO HACEN LOS CALCULOS PARA LA OXIGENACION DEL AGUA DISUELTO