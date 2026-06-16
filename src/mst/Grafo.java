package mst;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public Grafo() {
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    // Agregar vértice
    public void agregarVertice(String nombre) {
        Vertice v = new Vertice(nombre);
        if (!vertices.contains(v)) {
            vertices.add(v);
        }
    }

    // Agregar arista
    public void agregarArista(String origen, String destino, int peso) {
        Vertice vOrigen = new Vertice(origen);
        Vertice vDestino = new Vertice(destino);
        Arista arista = new Arista(vOrigen, vDestino, peso);
        aristas.add(arista);
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Aristas: ").append(aristas).append("\n");
        return sb.toString();
    }
}
