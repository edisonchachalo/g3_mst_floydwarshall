package floydwarshall;

public class ResultadoFW {
    public int[][] distancias;
    public int[][] predecesores;

    public ResultadoFW(int[][] distancias, int[][] predecesores) {
        this.distancias = distancias;
        this.predecesores = predecesores;
    }
}
