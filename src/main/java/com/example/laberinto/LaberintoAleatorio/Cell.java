package com.example.laberinto.LaberintoAleatorio;

public class Cell {
    private final int x;         // Coordenada x de la celda
    private final int y;         // Coordenada y de la celda
    private boolean visited;     // Indica si la celda ha sido visitada
    // Puedes agregar más atributos según tus necesidades

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;     // Inicialmente, la celda no ha sido visitada
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    // Puedes agregar más métodos según tus necesidades
}
