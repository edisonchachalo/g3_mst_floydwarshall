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
    private JTextArea textAreaFW;

    public Ventana(){

        btnGenerarFW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(txtVerticeFW.getText());
                    DefaultTableModel model = new DefaultTableModel(n, n);
                    tableFW.setModel(model);

                    // Etiquetas de columnas
                    for (int i = 0; i < n; i++) {
                        tableFW.getColumnModel().getColumn(i).setHeaderValue((char) ('A' + i));
                    }
                    tableFW.getTableHeader().repaint();

                    JOptionPane.showMessageDialog(FloydWarshall,
                            "Se ha generado una matriz de " + n + "x" + n + " para ingresar los pesos.",
                            "Matriz creada", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FloydWarshall,
                            "Ingrese un número válido de vértices.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnFW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = tableFW.getRowCount();
                int[][] matriz = new int[n][n];

                // Leer matriz de entrada
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Object valor = tableFW.getValueAt(i, j);
                        if (valor != null) {
                            try {
                                int peso = Integer.parseInt(valor.toString());
                                matriz[i][j] = (peso > 0) ? peso : Integer.MAX_VALUE / 2;
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

                // Mostrar resultados con rutas
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
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().FloydWarshall);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
