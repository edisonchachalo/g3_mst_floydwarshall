package mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    // Subconjunto para Union-Find
    private class Subconjunto {
        int padre, rango;
    }

    // Método principal para ejecutar Kruskal
    public List<Arista> ejecutarKruskal(Grafo grafo) {
        List<Arista> resultado = new ArrayList<>();

        // Ordenar las aristas por peso
        List<Arista> aristasOrdenadas = new ArrayList<>(grafo.getAristas());
        Collections.sort(aristasOrdenadas);

        int V = grafo.getVertices().size();
        Subconjunto[] subconjuntos = new Subconjunto[V];

        // Inicializar subconjuntos
        for (int i = 0; i < V; i++) {
            subconjuntos[i] = new Subconjunto();
            subconjuntos[i].padre = i;
            subconjuntos[i].rango = 0;
        }

        int e = 0; // contador de aristas en el MST
        int i = 0; // índice para recorrer aristas

        while (e < V - 1 && i < aristasOrdenadas.size()) {
            Arista siguienteArista = aristasOrdenadas.get(i++);
            int x = encontrar(subconjuntos, grafo.getVertices().indexOf(siguienteArista.getOrigen()));
            int y = encontrar(subconjuntos, grafo.getVertices().indexOf(siguienteArista.getDestino()));

            // Si no forma ciclo, incluir en resultado
            if (x != y) {
                resultado.add(siguienteArista);
                e++;
                unir(subconjuntos, x, y);
            }
        }

        return resultado;
    }

    // Método encontrar (Union-Find)
    private int encontrar(Subconjunto[] subconjuntos, int i) {
        if (subconjuntos[i].padre != i) {
            subconjuntos[i].padre = encontrar(subconjuntos, subconjuntos[i].padre);
        }
        return subconjuntos[i].padre;
    }

    // Método unir (Union-Find)
    private void unir(Subconjunto[] subconjuntos, int x, int y) {
        int xroot = encontrar(subconjuntos, x);
        int yroot = encontrar(subconjuntos, y);

        if (subconjuntos[xroot].rango < subconjuntos[yroot].rango) {
            subconjuntos[xroot].padre = yroot;
        } else if (subconjuntos[xroot].rango > subconjuntos[yroot].rango) {
            subconjuntos[yroot].padre = xroot;
        } else {
            subconjuntos[yroot].padre = xroot;
            subconjuntos[xroot].rango++;
        }
    }
}
