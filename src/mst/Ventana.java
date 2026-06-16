package mst;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Ventana {
    private JPanel MST;
    private JTextField txtVertice;
    private JTable tableMST;
    private JButton btnKruskal;
    private JButton btnPrim;
    private JTextArea textArea1;
    private JButton btnGenerar;

    public Ventana(){


        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(txtVertice.getText());
                    DefaultTableModel model = new DefaultTableModel(n, n);
                    tableMST.setModel(model);

                    // Etiquetas de columnas y filas
                    for (int i = 0; i < n; i++) {
                        tableMST.getColumnModel().getColumn(i).setHeaderValue((char) ('A' + i));
                    }
                    tableMST.getTableHeader().repaint();

                    JOptionPane.showMessageDialog(MST,
                            "Se ha generado una matriz de " + n + "x" + n + " para ingresar los pesos.",
                            "Tabla creada", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MST,
                            "Ingrese un número válido de vértices.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnKruskal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo grafo = construirGrafoDesdeTabla();
                KruskalMST kruskal = new KruskalMST();
                List<Arista> mst = kruskal.ejecutarKruskal(grafo);
                mostrarResultado("Kruskal", mst);
            }
        });
        btnPrim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo grafo = construirGrafoDesdeTabla();
                PrimMST prim = new PrimMST();
                List<Arista> mst = prim.ejecutarPrim(grafo);
                mostrarResultado("Prim", mst);
            }
        });
    }

    private Grafo construirGrafoDesdeTabla() {
        Grafo grafo = new Grafo();

        int n = tableMST.getRowCount(); // número de filas = número de vértices

        // Crear vértices con nombres A, B, C...
        for (int i = 0; i < n; i++) {
            String nombre = String.valueOf((char) ('A' + i));
            grafo.agregarVertice(nombre);
        }

        // Recorrer la matriz y agregar aristas
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) { // solo parte superior (grafo no dirigido)
                Object valor = tableMST.getValueAt(i, j);
                if (valor != null) {
                    try {
                        int peso = Integer.parseInt(valor.toString());
                        if (peso > 0) { // ignorar 0 o vacío
                            String origen = String.valueOf((char) ('A' + i));
                            String destino = String.valueOf((char) ('A' + j));
                            grafo.agregarArista(origen, destino, peso);
                        }
                    } catch (NumberFormatException e) {
                        // ignorar valores no numéricos
                    }
                }
            }
        }

        return grafo;
    }

    // Mostrar resultado en el JTextArea
    private void mostrarResultado(String algoritmo, List<Arista> mst) {
        StringBuilder sb = new StringBuilder("=== MST con " + algoritmo + " ===\n");
        int costo = 0;
        for (Arista a : mst) {
            sb.append(a).append("\n");
            costo += a.getPeso();
        }
        sb.append("Costo total: ").append(costo);
        textArea1.setText(sb.toString());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ÁRBOL DE EXPANSIÓN MÍNIMA - MST");
        frame.setContentPane(new Ventana().MST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
