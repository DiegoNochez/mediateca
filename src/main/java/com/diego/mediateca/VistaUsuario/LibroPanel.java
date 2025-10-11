package com.diego.mediateca.VistaUsuario;

import com.diego.mediateca.services.MaterialService;

import javax.swing.*;
import java.awt.*;

public class LibroPanel extends JPanel {
    private final MaterialService service;

    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtAutor = new JTextField(20);
    private final JTextField txtPaginas = new JTextField(8);
    private final JTextField txtEditorial = new JTextField(20);
    private final JTextField txtIsbn = new JTextField(16);
    private final JTextField txtAnio = new JTextField(6);
    private final JTextField txtUnidades = new JTextField(6);

    public LibroPanel(MaterialService service) {
        this.service = service;
        setLayout(new BorderLayout());
        add(buildForm(), BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private JPanel buildForm() {
        var form = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        int r = 0;
        addRow(form, gbc, r++, new JLabel("Título:"), txtTitulo);
        addRow(form, gbc, r++, new JLabel("Autor:"), txtAutor);
        addRow(form, gbc, r++, new JLabel("Páginas:"), txtPaginas);
        addRow(form, gbc, r++, new JLabel("Editorial:"), txtEditorial);
        addRow(form, gbc, r++, new JLabel("ISBN:"), txtIsbn);
        addRow(form, gbc, r++, new JLabel("Año publicación:"), txtAnio);
        addRow(form, gbc, r++, new JLabel("Unidades disponibles:"), txtUnidades);

        return form;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, JComponent label, JComponent field) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private JPanel buildButtons() {
        var panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        var btnGuardar = new JButton("Guardar Libro");
        var btnLimpiar = new JButton("Limpiar");
        panel.add(btnLimpiar);
        panel.add(btnGuardar);

        btnLimpiar.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardarLibro());
        return panel;
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtPaginas.setText("");
        txtEditorial.setText("");
        txtIsbn.setText("");
        txtAnio.setText("");
        txtUnidades.setText("");
        txtTitulo.requestFocus();
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int paginas = Integer.parseInt(txtPaginas.getText().trim());
            String editorial = txtEditorial.getText().trim();
            String isbn = txtIsbn.getText().trim();
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int unidades = Integer.parseInt(txtUnidades.getText().trim());

            var libro = service.agregarLibro(titulo, autor, paginas, editorial, isbn, anio, unidades);
            JOptionPane.showMessageDialog(this,
                    "Libro guardado:\n" + libro,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Revisa los campos numéricos (Páginas, Año, Unidades).",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this,
                    iae.getMessage(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
