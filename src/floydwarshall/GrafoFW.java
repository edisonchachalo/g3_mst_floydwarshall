package floydwarshall;

public class GrafoFW {
    private int[][] matriz;
    private int n; // número de vértices

    public GrafoFW(int n) {
        this.n = n;
        matriz = new int[n][n];

        // Inicializar con "infinito" excepto la diagonal
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    matriz[i][j] = Integer.MAX_VALUE / 2; // valor grande para representar infinito
                }
            }
        }
    }

    public void setPeso(int i, int j, int peso) {
        matriz[i][j] = peso;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getNumeroVertices() {
        return n;
    }
}
