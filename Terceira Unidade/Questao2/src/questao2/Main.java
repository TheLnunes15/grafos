package questao2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int dim;
        int[][] graph;
        try {
            Scanner in = new Scanner(new File("input.txt"));
            dim = in.nextInt();
            graph = new int[dim][dim];
            for(int i = 0; i < dim; i++) {
                for(int j = 0; j < dim; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
            Grafo gr = new Grafo(graph);
            System.out.println("Grafo de origem:\n" + gr);
            Faces planar = gr.getPlanarLaying();
            if(planar != null){
                System.out.println("O grafo eh planar!");
                System.out.println(planar);
            } else {
                System.out.println("O grafo nao eh planar :(");
            }
        } catch (FileNotFoundException e){
            e.getMessage();
        }
    }
}