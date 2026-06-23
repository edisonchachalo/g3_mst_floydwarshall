package floydwarshall;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana {
    private JPanel FloydWarshall;
    private JTextField txtVerticeFW;
    private JButton btnGenerarFW;
    private JTable tableFW;
    private JButton btnFW;
    private JTable tableResultadoFW;
    private JTextArea textAreaFW;
    private JTable tablePredecesoresFW;

    public Ventana(){

        // Acción para generar la matriz de adyacencia
        btnGenerarFW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(txtVerticeFW.getText());
                    DefaultTableModel model = new DefaultTableModel(n, n);
                    tableFW.setModel(model);

                    // Etiquetas de columnas (A, B, C...)
                    for (int i = 0; i < n; i++) {
                        tableFW.getColumnModel().getColumn(i).setHeaderValue((char) ('A' + i));
                    }
                    tableFW.getTableHeader().repaint();

                    JOptionPane.showMessageDialog(FloydWarshall,
                            "Se ha generado una matriz de " + n + "x" + n + " para ingresar los pesos.\n" +
                                    "Ingrese - si no existe conexión entre los vértices.",
                            "Matriz creada", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FloydWarshall,
                            "Ingrese un número válido de vértices.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para ejecutar el algoritmo Floyd-Warshall
        btnFW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int n = tableFW.getRowCount();
                int[][] matriz = new int[n][n];

                // Validar diagonal principal
                for (int i = 0; i < n; i++) {
                    Object valor = tableFW.getValueAt(i, i);
                    if (valor != null) {
                        try {
                            int peso = Integer.parseInt(valor.toString());
                            if (peso != 0) {
                                JOptionPane.showMessageDialog(FloydWarshall,
                                        "Error: la diagonal principal debe contener solo ceros.\n" +
                                                "El valor en la posición [" + (char)('A' + i) + "," + (char)('A' + i) + "] es " + peso + ".",
                                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                                return; // Detiene la ejecución del algoritmo
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(FloydWarshall,
                                    "Error: la diagonal principal debe contener solo ceros.",
                                    "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }


                // Leer matriz de entrada
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Object valor = tableFW.getValueAt(i, j);
                        if (valor != null) {
                            try {
                                int peso = Integer.parseInt(valor.toString());
                                String valorCelda = valor.toString().trim();
                                if (valorCelda.equals("-")) {
                                    matriz[i][j] = Integer.MAX_VALUE / 2; // representa infinito
                                } else {
                                    peso = Integer.parseInt(valorCelda);
                                    matriz[i][j] = peso;
                                }

                            } catch (NumberFormatException ex) {
                                matriz[i][j] = Integer.MAX_VALUE / 2;
                            }
                        } else {
                            matriz[i][j] = Integer.MAX_VALUE / 2;
                        }
                    }
                }

                // Ejecutar algoritmo
                FloydWarshall fw = new FloydWarshall();
                ResultadoFW resultado = fw.ejecutar(matriz);

                // Mostrar matriz resultado
                DefaultTableModel modelRes = new DefaultTableModel(n, n);
                tableResultadoFW.setModel(modelRes);

                for (int i = 0; i < n; i++) {
                    tableResultadoFW.getColumnModel().getColumn(i).setHeaderValue((char) ('A' + i));
                }
                tableResultadoFW.getTableHeader().repaint();

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        modelRes.setValueAt(
                                resultado.distancias[i][j] >= Integer.MAX_VALUE / 2 ? "∞" : resultado.distancias[i][j],
                                i, j
                        );
                    }
                }

                // Mostrar matriz de predecesores
                DefaultTableModel modelPred = new DefaultTableModel(n, n);
                tablePredecesoresFW.setModel(modelPred);

                // Etiquetas de columnas
                for (int i = 0; i < n; i++) {
                    tablePredecesoresFW.getColumnModel().getColumn(i).setHeaderValue((char) ('A' + i));
                }
                tablePredecesoresFW.getTableHeader().repaint();

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (resultado.predecesores[i][j] == -1) {
                            modelPred.setValueAt("–", i, j);
                        } else {
                            modelPred.setValueAt((char) ('A' + resultado.predecesores[i][j]), i, j);
                        }
                    }
                }

                // Mostrar resultados en el JTextArea
                StringBuilder sb = new StringBuilder("=== Resultados Floyd-Warshall ===\n");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (i != j && resultado.distancias[i][j] < Integer.MAX_VALUE / 2) {
                            sb.append("Distancia mínima entre ")
                                    .append((char) ('A' + i)).append(" y ")
                                    .append((char) ('A' + j)).append(" = ")
                                    .append(resultado.distancias[i][j]).append("\n")
                                    .append("Camino: ")
                                    .append(fw.reconstruirCamino(i, j, resultado.predecesores))
                                    .append("\n\n");
                        }
                    }
                }
                textAreaFW.setText(sb.toString());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Algoritmo Floyd-Warshall");
        frame.setContentPane(new Ventana().FloydWarshall);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
