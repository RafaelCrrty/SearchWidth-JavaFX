package com.example.laberinto.LaberintoAleatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    private final int rows;
    private final int cols;
    private Cell[][] grid;
    private final Random random;

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        matrizCoordenadas = new int[rows][cols];
        this.random = new Random();

        // Inicializa la cuadrícula y crea todas las celdas
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        // Genera números aleatorios para las coordenadas iniciales
        int initialX = random.nextInt(rows-1); // Número aleatorio entre 0 y (rows - 1)
        int initialY = random.nextInt(cols-1); // Número aleatorio entre 0 y (cols - 1)

        // Llama al método para generar el laberinto con las coordenadas iniciales aleatorias
        generateMaze(grid[initialX][initialY]);
    }

    public void liberar_memoria(){
        matrizCoordenadas = null;
    }

    private void generateMaze(Cell currentCell) {
        currentCell.setVisited(true);
        List<Cell> neighbors = getUnvisitedNeighbors(currentCell);

        if (!neighbors.isEmpty()) {
            Collections.shuffle(neighbors, random);
            for (Cell neighbor : neighbors) {
                if (!neighbor.isVisited()) {
                    // Elimina la pared entre la celda actual y la celda vecina
                    removeWall(currentCell, neighbor);
                    generateMaze(neighbor); // Llama recursivamente a la función para la celda vecina
                }
            }
        }
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell) {
        List<Cell> neighbors = new ArrayList<>();

        int x = cell.getX();
        int y = cell.getY();

        if (x > 1 && !grid[x - 2][y].isVisited()) {
            neighbors.add(grid[x - 2][y]);
        }
        if (x < rows - 2 && !grid[x + 2][y].isVisited()) {
            neighbors.add(grid[x + 2][y]);
        }
        if (y > 1 && !grid[x][y - 2].isVisited()) {
            neighbors.add(grid[x][y - 2]);
        }
        if (y < cols - 2 && !grid[x][y + 2].isVisited()) {
            neighbors.add(grid[x][y + 2]);
        }

        return neighbors;
    }

    private void removeWall(Cell current, Cell neighbor) {
        int x = (current.getX() + neighbor.getX()) / 2;
        int y = (current.getY() + neighbor.getY()) / 2;
        grid[x][y].setVisited(true);
    }
    private int [][] matrizCoordenadas;
    public int [][] generateprintMaze() {
        for (int i = 0; i < rows; i++) {
            // Imprime las paredes verticales y celdas internas
            for (int j = 0; j < cols; j++) {
                Cell cell = grid[i][j];
                if (cell.isVisited()) {
                    matrizCoordenadas[i][j] = 3;
                } else {
                    matrizCoordenadas[i][j] = 2;
                }
            }
        }
        grid = null;
        return matrizCoordenadas;
    }

}
