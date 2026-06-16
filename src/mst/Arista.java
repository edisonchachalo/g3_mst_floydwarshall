package mst;

public class Arista implements Comparable<Arista> {
    private Vertice origen;
    private Vertice destino;
    private int peso;

    public Arista(Vertice origen, Vertice destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return origen + " -- " + destino + " (peso: " + peso + ")";
    }

    // Para que Kruskal pueda ordenar las aristas por peso
    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.peso, otra.peso);
    }
}
