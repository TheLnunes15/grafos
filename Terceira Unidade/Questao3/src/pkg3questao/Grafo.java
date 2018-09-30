package pkg3questao;

import java.io.*;

import static java.lang.Integer.parseInt;

public class Grafo {
    private Vertices vertices[];

    public Grafo() throws IOException {
        try {
            this.makeGraphFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeGraphFromFile() throws IOException {
        File f = new File("input.txt");

    // verificando as linhas e numeros
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

   //  encontre o maior vertice
        int max = 0;
        while ((line = br.readLine()) != null) {
            String[] splitedVertices = line.split("\\s+");
            if (parseInt(splitedVertices[0]) > max) max = parseInt(splitedVertices[0]);
            if (parseInt(splitedVertices[1]) > max) max = parseInt(splitedVertices[1]);
        }

     // preenche a adjacencia de vertices
        vertices = new Vertices[max];
        for (int i = 0; i < max; i++)
            vertices[i] = new Vertices(i);

        br.close();
        br = new BufferedReader(new FileReader(f));
        while ((line = br.readLine()) != null) {

            String[] splitedVertices = line.split("\\s+");

            vertices[(parseInt(splitedVertices[0])) - 1].adjacencyVertices.add(parseInt(splitedVertices[1]));
            vertices[(parseInt(splitedVertices[1])) - 1].adjacencyVertices.add(parseInt(splitedVertices[0]));
        } 

        for (int i = 0; i < max; i++) {
            System.out.print("Vertice: ");
            System.out.println(i + 1);
            System.out.print("Caminho: ");
            for (int v1 : vertices[i].adjacencyVertices) {
                System.out.print(v1 + " - ");
            }
            System.out.println();
            System.out.println("-----------------------------");
        }        
        paint();
    }

    private void paint() {
        for (int i = 0; i < vertices.length; i++) {
            int maxColor = -1, minColor = -1;
            if (i == vertices.length-1) minColor++; maxColor++;
            int resultColor = 0;

            for (int j = 0; j < vertices[i].adjacencyVertices.size(); j++) {
                if (vertices[vertices[i].adjacencyVertices.elementAt(j)-1].getColor() > maxColor)
                    maxColor = vertices[vertices[i].adjacencyVertices.elementAt(j)-1].getColor();
            if (vertices[vertices[i].adjacencyVertices.elementAt(j)-1].getColor() < minColor)
                minColor = vertices[vertices[i].adjacencyVertices.elementAt(j)-1].getColor();
            }

            for (int j = minColor; j <= maxColor; j++) {
                boolean find = false;
                for (int v : vertices[i].adjacencyVertices){
                    if(vertices[v-1].getColor() == minColor && !find){
                        minColor++;
                        find = true;
                       break;
                    }
                }
            }
            resultColor = minColor;
            vertices[i].setColor(resultColor);
            System.out.print("Vertice: ");
            System.out.println((i+1) + " -> Cor: " + resultColor + " (" + Cores.values()[resultColor] + ")");
        }
    }
}
