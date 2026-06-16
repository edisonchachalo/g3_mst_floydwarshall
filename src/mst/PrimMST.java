package mst;

import java.util.*;

public class PrimMST {

    public List<Arista> ejecutarPrim(Grafo grafo) {
        List<Arista> resultado = new ArrayList<>();
        Set<Vertice> visitados = new HashSet<>();
        PriorityQueue<Arista> cola = new PriorityQueue<>();

        // Empezamos desde el primer vértice
        if (grafo.getVertices().isEmpty()) return resultado;
        Vertice inicio = grafo.getVertices().get(0);
        visitados.add(inicio);

        // Agregar todas las aristas que salen del vértice inicial
        for (Arista a : grafo.getAristas()) {
            if (a.getOrigen().equals(inicio) || a.getDestino().equals(inicio)) {
                cola.add(a);
            }
        }

        // Mientras haya aristas en la cola y no se visiten todos los vértices
        while (!cola.isEmpty() && visitados.size() < grafo.getVertices().size()) {
            Arista arista = cola.poll();

            Vertice v1 = arista.getOrigen();
            Vertice v2 = arista.getDestino();

            // Verificar si conecta un vértice nuevo
            if (visitados.contains(v1) && visitados.contains(v2)) {
                continue; // ya están conectados
            }

            resultado.add(arista);

            Vertice nuevo = visitados.contains(v1) ? v2 : v1;
            visitados.add(nuevo);

            // Agregar nuevas aristas que salen del vértice recién agregado
            for (Arista a : grafo.getAristas()) {
                if (a.getOrigen().equals(nuevo) || a.getDestino().equals(nuevo)) {
                    cola.add(a);
                }
            }
        }

        return resultado;
    }
}
