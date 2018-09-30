package pkg3questao;

import java.util.Vector;

public class Vertices {
    private int name, color;
    Vector<Integer> adjacencyVertices;

    public Vertices(int name) {
        this.name = name;
        adjacencyVertices = new Vector<>();
        color = -1;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return ""+(name+1);
    }
}
