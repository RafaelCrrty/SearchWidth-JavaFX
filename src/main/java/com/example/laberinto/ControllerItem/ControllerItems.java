package com.example.laberinto.ControllerItem;

import com.example.laberinto.HelloController;
import com.example.laberinto.Models.Component;
import com.example.laberinto.Models.Coordenadas;
import com.example.laberinto.Models.Mediator;
import com.example.laberinto.Observers.ObserbableControllers;
import com.example.laberinto.Observers.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;

import javax.swing.text.Element;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerItems implements Initializable, Observer {

    @FXML
    private AnchorPane root_color;
    private int x = 0,y = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public AnchorPane getRoot_color() {
        return root_color;
    }

    public void setRoot_color(AnchorPane root_color) {
        this.root_color = root_color;
    }
    @FXML
    void OnmouseclikedColor(MouseEvent event) {
        if(helloController == null){
            return;
        }
        if(option != -1){
            Coordenadas coordenadas = new Coordenadas(getX(),getY());
            component1.notify(coordenadas);
            if(option == 0){ // 0 -> Indico el Inicio (Inicio)
                eliminar_pasos_view();
                String inicio =helloController.coordenadas(coordenadas.getX(),getY(),0);
                if(!inicio.equals("i")){
                    Image image1 = new Image(getClass().getResourceAsStream("/com/example/laberinto/image/caminando.png"));
                    // Crear un ImageView y asignar la imagen
                    ImageView imageView = new ImageView(image1);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    // Agregar el ImageView al AnchorPane
                    root_color.getChildren().add(imageView);
                    root_color.setStyle("-fx-background-color: #0053a2; -fx-border-color: #bdbdbb;");
                }
                option = -1; // Reinicio la opcion para que no se agrege mas de un inicio
            }else if(option == 1){// 1 -> Indico el final  (Destino)
                eliminar_pasos_view();
                String fin =helloController.coordenadas(coordenadas.getX(),getY(),1);
                if(!fin.equals("f")){
                    Image image1 = new Image(getClass().getResourceAsStream("/com/example/laberinto/image/moto.png"));
                    // Crear un ImageView y asignar la imagen
                    ImageView imageView = new ImageView(image1);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setX(0);
                    imageView.setY(7);
                    // Agregar el ImageView al AnchorPane
                    root_color.getChildren().add(imageView);
                    root_color.setStyle("-fx-background-color: #009e2c; -fx-border-color: #bdbdbb;" );
                }
                option = -1; // Reinicio la opcion para que no se agrege mas de un final
            }else if(option == 2){ // 2 -> Indica Bloqueo
                eliminar_pasos_view();
                root_color.setStyle("-fx-background-color: #666665; -fx-border-color: #bdbdbb;");
                helloController.coordenadas(coordenadas.getX(),getY(),2);
            }else if(option == 3){// 3 -> Indica camino Libre
                eliminar_pasos_view();
                root_color.setStyle("-fx-background-color: #ffbc5c; -fx-border-color: #bdbdbb;");
                helloController.coordenadas(coordenadas.getX(),getY(),3);
            }
        }
    }

    public void eliminar_pasos_view(){
        ObservableList<Node> nodosParaEliminar = FXCollections.observableArrayList();
        for (Node node : root_color.getChildren()) {
            if (node instanceof ImageView) {
                nodosParaEliminar.add(node);
            }
        }
        root_color.getChildren().removeAll(nodosParaEliminar);
    }

    private Component component1;
    private Mediator mediator;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       mediator = Mediator.getInstance();
       component1 = new Component(mediator,"ControllerItems");
       mediator.add(component1);
       root_color.setStyle("-fx-background-color: #666665; -fx-border-color: #bdbdbb;");
    }

    private HelloController helloController;
    public void hellocontroller(HelloController helloController) {
    this.helloController = helloController;
    }

    private Integer option = -1;
    @Override
    public void update() {
        if(helloController == null){
            return;
        }
        Object message = component1.getMessage();
        if (message instanceof Integer) {
            Integer style = (Integer) message;
            option = style;
        }
    }

    @Override
    public void updateColors(int x, int y, int os) {
        if(x == getX() && y == getY()){
            if(os == -1){
                Image image1 = new Image(getClass().getResourceAsStream("/com/example/laberinto/image/huellas.png"));
                // Crear un ImageView y asignar la imagen
                ImageView imageView = new ImageView(image1);
                imageView.setFitWidth(35);
                imageView.setFitHeight(35);
                imageView.setX(8);
                imageView.setY(5);
                root_color.getChildren().add(imageView);
            } else if (os == 1) {
                eliminar_pasos_view();
            }
            root_color.setStyle("-fx-background-color: #ffbc5c; -fx-border-color: #bdbdbb;");

        }
    }

}
