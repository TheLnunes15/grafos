package pkg3questao;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Grafo g = new Grafo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
