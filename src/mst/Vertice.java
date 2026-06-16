package mst;

public class Vertice {
    private String nombre; // etiqueta del vértice, ej. "A", "B", "C"

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    // equals y hashCode son importantes para comparar vértices en estructuras
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vertice)) return false;
        Vertice v = (Vertice) obj;
        return nombre.equals(v.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}
