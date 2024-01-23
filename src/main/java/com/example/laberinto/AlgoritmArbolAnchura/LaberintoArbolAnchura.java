package com.example.laberinto.AlgoritmArbolAnchura;

import com.example.laberinto.Models.Coordenadas;
import javafx.scene.control.Alert;

import java.util.*;

public class LaberintoArbolAnchura {

    private int [][] matrizCoordenadas = {};
    private Coordenadas [][] matrizVisitados = {};
    private Queue<Coordenadas> cola = new LinkedList<>();// Crear una cola utilizando LinkedList
    private Stack<Coordenadas> recorrido = new Stack<>();
    private Coordenadas coordenadasIniciales;
    private int[] coordenada_destino = {-1,-1};

    public LaberintoArbolAnchura(int[][] matrizCoordenadas) {
        this.matrizCoordenadas = matrizCoordenadas;
        matrizVisitados = new Coordenadas[matrizCoordenadas.length][matrizCoordenadas[0].length];
    }



    public int[] laverinto_consume(){
        int coordenada_Inicial[] = laberinto_inicio();
        int coodenada_final[] = laberinto_fin();
        // No encontro un inicio
        if(coordenada_Inicial[0] == -1 && coordenada_Inicial[1] == -1){
            Alert alert = new Alert(Alert.AlertType.WARNING,"NO HAY UN INICIO FIJADO");
            alert.show();
            System.out.println("NO HAY INICIO");
            return coordenada_destino;
        }
        // No encontro una meta
        if(coodenada_final[0] == -1 && coodenada_final[1] == -1){
            Alert alert = new Alert(Alert.AlertType.WARNING,"NO HAY DESTINO FIJADO");
            alert.show();
            System.out.println("NO HAY FIN");
            return coordenada_destino;
        }
        // Creo una coordenada la cual es la inicial donde todo comeinza y empiazo a emplorar mi matriz
        Coordenadas coordenadas = new Coordenadas(coordenada_Inicial[0],coordenada_Inicial[1]);
        // Agrego en esa posicion inicial como visitado
        matrizVisitados[coordenadas.getX()][coordenadas.getY()] = coordenadas;
        // Se agrega a la cola
        cola.add(coordenadas);
        // Inicializo mi objeto con la coordenadas iniciales para validaciones posteriores
        coordenadasIniciales = new Coordenadas(coordenadas.getX(),coordenadas.getY());

        // Bucle infinito hasta que sea falso
        Boolean laberinto = true;
        while(laberinto) {
            // Verificar que la cola no este vacia
            if (!cola.isEmpty()) {
                // Si no esta vacia recuperar el elemento al frente de la cola
                int x = cola.peek().getX();
                int y = cola.peek().getY();
                // Empiezo a moverme en mi laberinto y donde la posicion no este marcada
                // Como visitada, la agrego a la cola
                laverinto_move(x,y);
                // Si en la matriz de coordenadas de mi laberinto esa posicion contiene la meta
                if (matrizCoordenadas[x][y] == 1) {
                    // Entonces recupero esa posicion y mi condicion cambiara a falso
                    coordenada_destino[0] = x;
                    coordenada_destino[1] = y;
                   laberinto = false;
                }
                // Si encontro la meta, lo agrego a la pila de mi recorrido
                if(laberinto==false){
                    System.out.println(cola.peek().getX()+" .. "+cola.peek().getY());
                    recorrido.add(cola.peek());
                }else {
                    // Caso contrario remuevo el nodo al frente de la cola
                    cola.remove();
                }
            }
            // Si me quede sin camino, no hay solucion al laberinto
            if(cola.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING,"NO HAY SOLUCION PARA EL LABERINTO");
                alert.show();
                System.out.println("NO HAY SOLUCION PARA EL LABERINTO");
               return coordenada_destino;
            }
        }
        for (int i = 0; i < matrizVisitados.length; i++) { // FILAS
            for (int j = 0; j < matrizVisitados[0].length; j++) { // COLUMNAS
                if (matrizVisitados[i][j] != null) {
                    System.out.print("(" + matrizVisitados[i][j].getX());
                    System.out.print("," + matrizVisitados[i][j].getY() + ")");
                } else {
                    System.out.print("(null,null)");
                }
                System.out.print(" ");
            }
            System.out.println(); // Mover esta línea aquí para imprimir en una nueva línea después de cada fila
        }
        recorrido_laberinto();
        return coordenada_destino;
    }

    // Ruta optima
    private void recorrido_laberinto() {
        int x = -1,y=-1;
        /* El recorrido no debe estar vacio
           Recordemos que cuando se encuentra la meta
           Esa posicion se agrega a la pila*/
        if(recorrido.isEmpty()){
            return;
        }
        while(true) {// Obtenemos de la pila las coordenadas de la meta
            x = recorrido.peek().getX();
            y = recorrido.peek().getY();
            // Obtenemos las coordenadas de la matriz de visitados en esa posicion
            Coordenadas coordenadas = matrizVisitados[x][y];
            // Limpiamos el espacio de memoria
            matrizVisitados[x][y] = null;
            // Añadimos las coordenadas de la matriz de visitados a la pila
            recorrido.add(coordenadas);
            // Siempre y cuando sea diferente coordenadas de null
            if(coordenadas != null){
                // Dejamos de hacer todo lo anterior asta econtrar las coordenas iniciales es decir nuestro Inicio
                if(coordenadas.getX()==coordenadasIniciales.getX() && coordenadas.getY()==coordenadasIniciales.getY()){
                    break;
                }
            }
        }
        // Dejamos todo en null
        matrizVisitados = null;
        matrizCoordenadas = null;
    }

    public boolean laverinto_move(int x, int y) {
        boolean visita = false; // validar que se pueda mover para todos lados
        if (y + 1 < matrizCoordenadas[0].length && (matrizCoordenadas[x][y + 1] == 3 || matrizCoordenadas[x][y + 1] == 1 )) { // Derecha
            int x_filas = 0,y_columnas = 0;
            y_columnas = y + 1;
            x_filas = x;
           Coordenadas coordenadas = new Coordenadas(x_filas,y_columnas);
           if(matrizVisitados[x_filas][y_columnas] == null){
               // NECESITO AGREGAR LA COORDENADA DEL PADRE EN LA COORDENADA DEL HIJO
               Coordenadas clonnew = new Coordenadas(x_filas,y_columnas-1);
               matrizVisitados[coordenadas.getX()][coordenadas.getY()] = clonnew;
               cola.add(coordenadas);
               visita = true;
           }
       }
        if (y - 1 >= 0 && y - 1 < matrizCoordenadas[0].length && (matrizCoordenadas[x][y - 1] == 3 || matrizCoordenadas[x][y - 1] == 1)) {// Izquierda
           int x_filas = 0,y_columnas = 0;
           y_columnas = y - 1;
           x_filas = x;
           Coordenadas coordenadas = new Coordenadas(x_filas,y_columnas);
           if(matrizVisitados[x_filas][y_columnas] == null){
               // NECESITO AGREGAR LA COORDENADA DEL PADRE EN LA COORDENADA DEL HIJO
               Coordenadas clonnew = new Coordenadas(x_filas,y_columnas+1);
               matrizVisitados[coordenadas.getX()][coordenadas.getY()] = clonnew;
               cola.add(coordenadas);
               visita = true;
           }
       }
       if (x + 1 < matrizCoordenadas.length && (matrizCoordenadas[x + 1][y] == 3 || matrizCoordenadas[x+1][y] == 1)) { // Abajo
           int x_filas = 0,y_columnas = 0;
           x_filas = x + 1;
           y_columnas = y;
           Coordenadas coordenadas = new Coordenadas(x_filas,y_columnas);
           if(matrizVisitados[x_filas][y_columnas] == null){
               // NECESITO AGREGAR LA COORDENADA DEL PADRE EN LA COORDENADA DEL HIJO
               Coordenadas clonnew = new Coordenadas(x_filas-1,y_columnas);
               matrizVisitados[coordenadas.getX()][coordenadas.getY()] = clonnew;
               cola.add(coordenadas);
               visita = true;
           }
       }
       if (x - 1 >= 0  && x - 1  < matrizCoordenadas.length && (matrizCoordenadas[x - 1][y] == 3 || matrizCoordenadas[x-1][y] == 1)) { // Arriba
           int x_filas = 0,y_columnas = 0;
           x_filas = x - 1;
           y_columnas = y;
           Coordenadas coordenadas = new Coordenadas(x_filas,y_columnas);
           if(matrizVisitados[x_filas][y_columnas] == null){
               // NECESITO AGREGAR LA COORDENADA DEL PADRE EN LA COORDENADA DEL HIJO
               Coordenadas clonnew = new Coordenadas(x_filas+1,y_columnas);
               matrizVisitados[coordenadas.getX()][coordenadas.getY()] = clonnew;
               cola.add(coordenadas);
               visita = true;
           }
       }
       return visita;
    }

    public int[] laberinto_inicio(){
        int [] solveIJ = {-1,-1};
        for (int i = 0;i<matrizCoordenadas.length;i++){ // FILAS
            for (int j = 0;j<matrizCoordenadas[0].length;j++){ // COLUMNAS
                if(matrizCoordenadas[i][j] == 0){
                    solveIJ[0] = i;
                    solveIJ[1] = j;
                    return solveIJ;
                }
            }
        }
        return solveIJ;
    }

    public int[] laberinto_fin(){
        int [] solveIJ = {-1,-1};
        for (int i = 0;i<matrizCoordenadas.length;i++){ // FILAS
            for (int j = 0;j<matrizCoordenadas[0].length;j++){ // COLUMNAS
                if(matrizCoordenadas[i][j] == 1){
                    solveIJ[0] = i;
                    solveIJ[1] = j;
                    return solveIJ;
                }
            }
        }
        return solveIJ;
    }

    public int[][] getMatrizCoordenadas() {
        return matrizCoordenadas;
    }

    public void setMatrizCoordenadas(int[][] matrizCoordenadas) {
        this.matrizCoordenadas = matrizCoordenadas;
    }

    public Coordenadas[][] getMatrizVisitados() {
        return matrizVisitados;
    }

    public void setMatrizVisitados(Coordenadas[][] matrizVisitados) {
        this.matrizVisitados = matrizVisitados;
    }

    public Queue<Coordenadas> getCola() {
        return cola;
    }

    public void setCola(Queue<Coordenadas> cola) {
        this.cola = cola;
    }

    public Stack<Coordenadas> getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(Stack<Coordenadas> recorrido) {
        this.recorrido = recorrido;
    }
}
