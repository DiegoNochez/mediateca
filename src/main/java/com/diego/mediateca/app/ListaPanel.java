package com.diego.mediateca.app;

import com.diego.mediateca.services.MaterialService;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class ListaPanel extends JPanel {

    private final MaterialService service;
    private final String tipo; // "LIBRO", "REVISTA", "DVD", "CD"

    private final MaterialTableModel model = new MaterialTableModel();
    private final JTable tbl = new JTable(model);
    private final JTextField txtBuscar = new JTextField(22);
    private final JButton btnRefrescar = new JButton("Refrescar");
    private final JLabel lblTitulo = new JLabel();

    private final TableRowSorter<MaterialTableModel> sorter = new TableRowSorter<>(model);

    public ListaPanel(MaterialService service, String tipo) {
        this.service = service;
        this.tipo = tipo;

        setLayout(new BorderLayout(8,8));
        add(buildTopBar(), BorderLayout.NORTH);

        tbl.setRowSorter(sorter);
        tbl.setFillsViewportHeight(true);
        add(new JScrollPane(tbl), BorderLayout.CENTER);

        // Buscar en vivo
        txtBuscar.getDocument().addDocumentListener(SimpleDocs.onChange(() -> {
            String q = txtBuscar.getText().trim();
            sorter.setRowFilter(q.isEmpty() ? null : RowFilter.regexFilter("(?i)" + q));
        }));

        // Refrescar datos
        btnRefrescar.addActionListener(e -> cargarDatos());

        // Cargar al iniciar
        cargarDatos();
    }

    private JComponent buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(8,8));

        lblTitulo.setText("Disponibles: " + tipo);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 16f));
        top.add(lblTitulo, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        right.add(new JLabel("Buscar:"));
        right.add(txtBuscar);
        right.add(btnRefrescar);
        top.add(right, BorderLayout.EAST);

        return top;
    }

   private void cargarDatos() {
    try {
        var materiales = service.listarDisponiblesPorTipo(tipo); // List<Material>
        var data = new ArrayList<Object[]>();

        for (var m : materiales) {
            String codigo = m.getIdInterno();       // en Material
            String titulo = m.getTitulo();          // en Material

            // Autor/Director/Artista unificado
            String autorDir = "";
            if (m instanceof com.diego.mediateca.domain.Libro lib) {
                autorDir = lib.getAutor();
            } else if (m instanceof com.diego.mediateca.domain.Revista rev) {
                autorDir = rev.getEditorial(); // o deja "" si prefieres
            } else if (m instanceof com.diego.mediateca.domain.DVD dvd) {
                autorDir = dvd.getDirector();
            } else if (m instanceof com.diego.mediateca.domain.CD cd) {
                autorDir = cd.getArtista();
            }

            // Año (si aplica)
            Object anio = "";
            if (m instanceof com.diego.mediateca.domain.Libro lib2) {
                anio = lib2.getAnioPublicacion();
            } else if (m instanceof com.diego.mediateca.domain.Revista rev2) {
                // Mostrar año de la fecha de publicación
                anio = (rev2.getFechaPublicacion() != null) ? rev2.getFechaPublicacion().getYear() : "";
            } else {
                anio = ""; // DVD/CD no tienen año explícito en tu modelo
            }

            int unidades = m.getUnidadesDisponibles(); // en Material

            data.add(new Object[]{ codigo, titulo, autorDir, anio, unidades });
        }

        model.setData(data);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al cargar datos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}
