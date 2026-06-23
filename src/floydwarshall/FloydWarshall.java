package floydwarshall;

public class FloydWarshall {

    public ResultadoFW ejecutar(int[][] matriz) {
        int n = matriz.length;
        int[][] dist = new int[n][n];
        int[][] predecesor = new int[n][n];

        // Inicialización
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = matriz[i][j];
                if (i != j && matriz[i][j] < Integer.MAX_VALUE / 2) {
                    predecesor[i][j] = i;
                } else {
                    predecesor[i][j] = -1;
                }
            }
        }

        // Algoritmo Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        predecesor[i][j] = predecesor[k][j];
                    }
                }
            }
        }

        return new ResultadoFW(dist, predecesor);
    }

    // Método para reconstruir el camino
    public String reconstruirCamino(int i, int j, int[][] predecesor) {
        if (predecesor[i][j] == -1) return "Sin conexión";
        StringBuilder camino = new StringBuilder();
        reconstruirRecursivo(i, j, predecesor, camino);
        return camino.toString();
    }

    private void reconstruirRecursivo(int i, int j, int[][] predecesor, StringBuilder camino) {
        if (i != j) {
            reconstruirRecursivo(i, predecesor[i][j], predecesor, camino);
        }
        camino.append((char) ('A' + j));
        if (j != predecesor.length - 1) camino.append(" - ");
    }
}
