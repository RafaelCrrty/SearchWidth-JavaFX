package com.example.laberinto;

import com.example.laberinto.AlgoritmArbolAnchura.LaberintoArbolAnchura;
import com.example.laberinto.ControllerItem.ControllerItems;
import com.example.laberinto.LaberintoAleatorio.MazeGenerator;
import com.example.laberinto.Models.Component;
import com.example.laberinto.Models.Coordenadas;
import com.example.laberinto.Models.Mediator;
import com.example.laberinto.Observers.ObserbableControllers;
import com.example.laberinto.Observers.Observer;
import javafx.event.ActionEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;
import javax.imageio.ImageIO;

public class HelloController implements Initializable, Observer {

    @FXML
    private Button bnt_BorrarCamino;

    @FXML
    private Button bnt_colorearCamino;

    @FXML
    private Button bnt_destino;

    @FXML
    private Button bnt_inicio;

    @FXML
    private Button btn_calcularMatriz;

    @FXML
    private Button btn_eliminarTodo;

    @FXML
    private Button btn_resolver;
    @FXML
    private TextField txt_numcolumnas;

    @FXML
    private AnchorPane anchorpane_Screen;
    @FXML
    private TextField txt_numfilas;

    @FXML
    private VBox vbox_filas;

    private ObserbableControllers obserbableControllers;
    @FXML
    void OnActionLaberinto(ActionEvent event) {
        if(bnt_inicio == event.getSource()){
            component1.notify(0);
            obserbableControllers.notifyObserver();
        } else if (bnt_destino == event.getSource()) {
            component1.notify(1);
            obserbableControllers.notifyObserver();
        }else if(bnt_colorearCamino == event.getSource()){
            component1.notify(3);
            obserbableControllers.notifyObserver();
        } else if (bnt_BorrarCamino == event.getSource()) {
            component1.notify(2);
            obserbableControllers.notifyObserver();
        } else if (btn_eliminarTodo == event.getSource()) {
            if(recorridoStackAnterior != null || !recorridoStackAnterior.isEmpty()){
                colorear_ruta(recorridoStackAnterior,1);
                recorridoStackAnterior.clear();
                bnt_BorrarCamino.setDisable(false);
                bnt_colorearCamino.setDisable(false);
                bnt_destino.setDisable(false);
                bnt_inicio.setDisable(false);
                btn_eliminarTodo.setDisable(true);
                btn_resolver.setDisable(false);
            }
        }
    }

    @FXML
    void OnclicActionGenerarMatriz(ActionEvent event) {
        rellenado_Matriz();
    }

    @FXML
    void OnclicActionGenerarLBRNT(ActionEvent event) {
        rellenado_Matriz();
        int numFilas = Integer.parseInt(txt_numfilas.getText());
        int numColumnas = Integer.parseInt(txt_numcolumnas.getText());
        MazeGenerator mazeGenerator = new MazeGenerator(numFilas, numColumnas);
        // Imprime o muestra el laberinto generado
        int matriz[][] = mazeGenerator.generateprintMaze(); // Implementa este método para visualizar el laberinto
        for(int i = 0; i<numFilas; i++){
            for(int j = 0 ; j<numColumnas; j++){
                if(matriz[i][j]!=2){
                    obserbableControllers.notifyObserverRecorrido(i,j,0);
                }
            }
        }
        matrizCoordenadas = matriz;
    }


    private Stack<Coordenadas> recorridoStackAnterior;
    @FXML
    void OnActionLaberinto_Resolver(ActionEvent event) {
        if (!(matrizCoordenadas.length > 0 && matrizCoordenadas[0].length > 0)) {
            System.out.println("MATRIZ VACIA");
            return;
        }
       LaberintoArbolAnchura laberintoArbolAnchura = new LaberintoArbolAnchura(matrizCoordenadas);
       int destino[] =  laberintoArbolAnchura.laverinto_consume();
        if(laberintoArbolAnchura.getRecorrido().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING,"NO HAY RECORRIDO EFICIENTE");
            alert.show();
            return;
        }
        recorridoStackAnterior = new Stack<>();
        recorridoStackAnterior.addAll(laberintoArbolAnchura.getRecorrido());
        Stack<Coordenadas> recorridoStack = laberintoArbolAnchura.getRecorrido();
        colorear_ruta(recorridoStack,-1);
        // Captura el contenido del AnchorPane en una imagen
        WritableImage image = capturePane(anchorpane_Screen);
        // Guarda la imagen en formato PNG
        try {
            File file = new File("captured_image.png");
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Imagen guardada como captured_image.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bnt_BorrarCamino.setDisable(true);
        bnt_colorearCamino.setDisable(true);
        bnt_destino.setDisable(true);
        bnt_inicio.setDisable(true);
        btn_resolver.setDisable(true);
        btn_eliminarTodo.setDisable(false);
        component1.notify(-1);
        obserbableControllers.notifyObserver();
        if(destino[0] != -1 && destino[1] != -1){
            String msj = "ENCONTRE LA MOTO DEL KEVIN EN: ("+destino[0]+","+destino[1]+")";
            Alert alert = new Alert(Alert.AlertType.INFORMATION,msj);
            alert.show();
        }
    }

    // Método para capturar el contenido de un Node en un WritableImage
    private WritableImage capturePane(AnchorPane pane) {

        WritableImage image = new WritableImage((int) pane.getWidth(), (int) pane.getHeight());
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT); // Fondo transparente
        pane.snapshot(parameters, image);

        return image;
    }

    public void colorear_ruta(Stack<Coordenadas> recorridoStack, int num){
        int total = recorridoStack.size()-1;
        int count = 0;
        while (!recorridoStack.isEmpty()) {
            Coordenadas elemento = recorridoStack.pop();
            if(!(count == 0 || count == total)){
                // num = -1 para solo agregar el png de pasos
                obserbableControllers.notifyObserverRecorrido(elemento.getX(), elemento.getY(),num);
            }
            count++;
        }
    }

    private int matrizCoordenadas [][] = {};
    public void rellenado_Matriz(){
        if(txt_numfilas.getText().length()==0 || txt_numcolumnas.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.WARNING,"NO HAY ESPACIO DEFINIDIO PARA EL LABERINTO");
            alert.show();
            System.out.println("NO HAY LONGITUD DEFINIDIA");
            return;
        }
        vbox_filas.getChildren().clear();
        int numFilas = Integer.parseInt(txt_numfilas.getText());
        int numColumnas = Integer.parseInt(txt_numcolumnas.getText());
        matrizCoordenadas = new int[numFilas][numColumnas];
        obserbableControllers = new ObserbableControllers();
        for(int i = 0; i<numFilas;i++){
            HBox hBox = new HBox();
            vbox_filas.getChildren().add(hBox);
            for(int j = 0 ; j<numColumnas;j++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/laberinto/ViewItemColor/viewitemcolor.fxml"));
                try {
                    AnchorPane anchorPane = loader.load();
                    ControllerItems controllerItems = loader.getController();
                    obserbableControllers.addObserver(controllerItems);
                    controllerItems.hellocontroller(this);
                    controllerItems.setX(i);
                    controllerItems.setY(j);
                    matrizCoordenadas [i][j] = 2;
                    hBox.getChildren().add(anchorPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        btn_eliminarTodo.setDisable(true);
        bnt_BorrarCamino.setDisable(false);
        bnt_colorearCamino.setDisable(false);
        bnt_destino.setDisable(false);
        bnt_inicio.setDisable(false);
        btn_resolver.setDisable(false);
        inicio = 0;
        fin= 0;
    }

    private Component component1;
    private Mediator mediator;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediator = Mediator.getInstance();
        component1 = new Component(mediator,"HelloController");
        mediator.add(component1);
        btn_eliminarTodo.setDisable(true);
        bnt_BorrarCamino.setDisable(true);
        bnt_colorearCamino.setDisable(true);
        bnt_destino.setDisable(true);
        bnt_inicio.setDisable(true);
        btn_resolver.setDisable(true);
    }

    int inicio = 0,fin= 0;
    int xia = -1,yia = -1;
    int xfa = -1,yfa = -1;
    public String coordenadas(int x,int y, int post){ // VALIDAR QUE SOLO HAYA UN INICIO Y FIN
        if(post == 0){
            if(inicio == 0){
                xia = x;
                yia = y;
            }
            if(inicio == 1){
                Alert alert = new Alert(Alert.AlertType.WARNING,"YA HAY UN INICIO");
                alert.show();
                return "i";
            }
            System.out.println(inicio);
            inicio = 1;
        } else if(post == 1){
            if(fin == 0){
                xfa = x;
                yfa = y;
            }
            if(fin == 1){
                Alert alert = new Alert(Alert.AlertType.WARNING,"YA HAY UN DESTINO");
                alert.show();
                return "f";
            }
            fin = 1;
        }
        if(post== 3 || post == 2){
            if(xia !=-1 && yia != -1){
                if(xia == x && yia == y){
                    inicio = 0;
                }
            }
            if(xfa != -1 && yfa != -1){
                if(xfa == x && yfa == y){
                    fin = 0;
                }
            }
        }
        matrizCoordenadas[x][y] = post;
        return "n";
    }
    @Override
    public void update() {

    }

    @Override
    public void updateColors(int x, int y, int os) {

    }

}