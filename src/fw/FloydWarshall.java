package fw;

public class FloydWarshall {
    private int[][] distancias;
    private int[][] predecesores;

    public void ejecutar(int[][] matriz) {
        int n = matriz.length;
        distancias = new int[n][n];
        predecesores = new int[n][n];

        // Inicialización
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distancias[i][j] = matriz[i][j];
                if (i != j && matriz[i][j] < Integer.MAX_VALUE / 2) {
                    predecesores[i][j] = i;
                } else {
                    predecesores[i][j] = -1;
                }
            }
        }

        // Algoritmo Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = predecesores[k][j];
                    }
                }
            }
        }
    }

    // Getter para distancias
    public int[][] getDistancias() {
        return distancias;
    }

    // Getter para predecesores
    public int[][] getPredecesores() {
        return predecesores;
    }

    // Método para reconstruir el camino
    public String reconstruirCamino(int i, int j) {
        if (predecesores[i][j] == -1) return "Sin conexión";
        StringBuilder camino = new StringBuilder();
        reconstruirRecursivo(i, j, camino);
        return camino.toString();
    }

    private void reconstruirRecursivo(int i, int j, StringBuilder camino) {
        if (i != j) {
            reconstruirRecursivo(i, predecesores[i][j], camino);
        }
        camino.append((char) ('A' + j));
        if (j != predecesores.length - 1) camino.append(" - ");
    }
}
