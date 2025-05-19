// ConversorSwingModerno.java
package lad.com.alura.conversormoneda;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class ConversorSwingModerno extends JFrame {
    private JComboBox<String> monedaOrigen, monedaDestino;
    private JTextField montoInput;
    private JLabel resultadoLabel;
    private JTable historialTabla;
    private DefaultTableModel historialModelo;
    private boolean modoOscuro = false;

    private JPanel historialSuperior; // <--- convertido en campo para poder cambiar color

    private static final Color COLOR_CLARO = new Color(240, 240, 240);
    private static final Color COLOR_OSCURO = new Color(40, 40, 40);
    private static final Color COLOR_PRIMARIO = new Color(52, 152, 219);
    private static final Color COLOR_SECUNDARIO = new Color(41, 128, 185);
    private static final Color COLOR_TEXTO_OSCURO = new Color(220, 220, 220);
    private static final Color COLOR_TABLA_OSCURA = new Color(60, 60, 60);

    private static final Map<String, Double> tasasConversion = new HashMap<>();
    static {
        tasasConversion.put("USD", 1.0);
        tasasConversion.put("EUR", 0.91);
        tasasConversion.put("GBP", 0.78);
        tasasConversion.put("JPY", 145.76);
        tasasConversion.put("CNY", 7.2);
        tasasConversion.put("CHF", 0.89);
        tasasConversion.put("CAD", 1.36);
        tasasConversion.put("AUD", 1.52);
        tasasConversion.put("MXN", 17.25);
        tasasConversion.put("BRL", 5.02);
        tasasConversion.put("INR", 83.12);
        tasasConversion.put("ARS", 890.0);
        tasasConversion.put("BOB", 6.89);
        tasasConversion.put("CLP", 932.50);
        tasasConversion.put("COP", 3902.0);
    }

    public ConversorSwingModerno() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Conversor de Monedas");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Conversor de Monedas");
        titulo.setFont(new Font("Roboto", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        monedaOrigen = new JComboBox<>(tasasConversion.keySet().toArray(new String[0]));
        monedaDestino = new JComboBox<>(tasasConversion.keySet().toArray(new String[0]));
        estilizarComboBox(monedaOrigen);
        estilizarComboBox(monedaDestino);
        panel.add(monedaOrigen);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(monedaDestino);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        montoInput = new JTextField(10);
        estilizarCampoTexto(montoInput);
        panel.add(montoInput);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton convertirBtn = crearBoton("Convertir", COLOR_PRIMARIO, e -> convertirMoneda());
        panel.add(convertirBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        resultadoLabel = new JLabel("Resultado aquí");
        resultadoLabel.setFont(new Font("Open Sans", Font.BOLD, 20));
        resultadoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultadoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        historialModelo = new DefaultTableModel();
        historialModelo.addColumn("Fecha");
        historialModelo.addColumn("Origen");
        historialModelo.addColumn("Destino");
        historialModelo.addColumn("Monto");
        historialModelo.addColumn("Resultado");

        historialTabla = new JTable(historialModelo);
        estilizarTabla(historialTabla);

        JScrollPane scrollPane = new JScrollPane(historialTabla);

        // Ahora historialSuperior es campo
        historialSuperior = new JPanel(new BorderLayout());
        historialSuperior.setBackground(panel.getBackground());
        historialSuperior.add(scrollPane, BorderLayout.CENTER);

        JButton btnBorrarHistorial = new JButton("🗑");
        btnBorrarHistorial.setToolTipText("Borrar historial");
        btnBorrarHistorial.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnBorrarHistorial.setFocusPainted(false);
        btnBorrarHistorial.setContentAreaFilled(false);
        btnBorrarHistorial.setBorderPainted(false);
        btnBorrarHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBorrarHistorial.setForeground(Color.GRAY);

        btnBorrarHistorial.addActionListener(e -> historialModelo.setRowCount(0));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnBorrarHistorial);

        historialSuperior.add(panelBoton, BorderLayout.NORTH);

        panel.add(historialSuperior);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnModoOscuro = crearBoton("Modo Oscuro", COLOR_SECUNDARIO, e -> alternarModoOscuro(panel, titulo));
        panel.add(btnModoOscuro);

        add(panel, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, Color color, ActionListener accion) {
        JButton boton = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
                super.paintComponent(g);
            }

            @Override protected void paintBorder(Graphics g) {
                if (g instanceof Graphics2D g2) {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground().darker());
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                }
            }
        };

        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Open Sans", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 40));
        boton.setMaximumSize(new Dimension(250, 45));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setContentAreaFilled(false);
        boton.addActionListener(accion);

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(color.darker());
                boton.repaint();
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(color);
                boton.repaint();
            }
        });

        return boton;
    }

    private void convertirMoneda() {
        try {
            double monto = Double.parseDouble(montoInput.getText());
            String monedaOrigenSeleccionada = (String) monedaOrigen.getSelectedItem();
            String monedaDestinoSeleccionada = (String) monedaDestino.getSelectedItem();
            double tasaOrigen = tasasConversion.getOrDefault(monedaOrigenSeleccionada, 1.0);
            double tasaDestino = tasasConversion.getOrDefault(monedaDestinoSeleccionada, 1.0);
            double resultado = (monto / tasaOrigen) * tasaDestino;

            resultadoLabel.setText(String.format("Resultado: %.2f %s", resultado, monedaDestinoSeleccionada));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            historialModelo.addRow(new Object[]{
                    sdf.format(new Date()), monedaOrigenSeleccionada, monedaDestinoSeleccionada,
                    String.format("%.2f", monto), String.format("%.2f %s", resultado, monedaDestinoSeleccionada)
            });

        } catch (NumberFormatException ex) {
            resultadoLabel.setText("Ingrese un monto válido");
        }
    }

    private void estilizarComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Open Sans", Font.PLAIN, 16));
        comboBox.setMaximumSize(new Dimension(250, 35));
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setMaximumSize(new Dimension(250, 35));
        campo.setFont(new Font("Open Sans", Font.PLAIN, 14));
        campo.setHorizontalAlignment(JTextField.CENTER);
        campo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Open Sans", Font.PLAIN, 14));
        tabla.setRowHeight(25);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void alternarModoOscuro(JPanel panel, JLabel titulo) {
        modoOscuro = !modoOscuro;
        Color fondo = modoOscuro ? COLOR_OSCURO : COLOR_CLARO;
        Color texto = modoOscuro ? COLOR_TEXTO_OSCURO : Color.BLACK;

        panel.setBackground(fondo);
        titulo.setForeground(texto);
        resultadoLabel.setForeground(texto);

        montoInput.setForeground(texto);
        montoInput.setBackground(modoOscuro ? Color.DARK_GRAY : Color.WHITE);
        montoInput.setCaretColor(texto);

        historialSuperior.setBackground(fondo); // <--- cambio para la barra del historial

        historialTabla.setBackground(modoOscuro ? COLOR_TABLA_OSCURA : Color.WHITE);
        historialTabla.setForeground(texto);
        historialTabla.setSelectionBackground(modoOscuro ? new Color(100, 100, 100) : new Color(200, 200, 255));
        historialTabla.setSelectionForeground(texto);
        historialTabla.getTableHeader().setBackground(modoOscuro ? new Color(30, 30, 30) : new Color(220, 220, 220));
        historialTabla.getTableHeader().setForeground(texto);

        SwingUtilities.updateComponentTreeUI(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConversorSwingModerno::new);
    }
}
