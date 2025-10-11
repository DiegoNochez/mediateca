package com.diego.mediateca.VistaUsuario;

import com.diego.mediateca.services.MaterialService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RevistaPanel extends JPanel {
    private final MaterialService service;

    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtEditorial = new JTextField(20);
    private final JComboBox<String> cbPeriodicidad = new JComboBox<>(new String[]{"Mensual","Semanal","Bimestral","Trimestral","Anual"});
    private final JTextField txtFecha = new JTextField(10); // formato yyyy-MM-dd
    private final JTextField txtUnidades = new JTextField(6);

    public RevistaPanel(MaterialService service) {
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
        addRow(form, gbc, r++, new JLabel("Editorial:"), txtEditorial);
        addRow(form, gbc, r++, new JLabel("Periodicidad:"), cbPeriodicidad);
        addRow(form, gbc, r++, new JLabel("Fecha publicación (yyyy-MM-dd):"), txtFecha);
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
        var btnGuardar = new JButton("Guardar Revista");
        var btnLimpiar = new JButton("Limpiar");
        panel.add(btnLimpiar);
        panel.add(btnGuardar);

        btnLimpiar.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardarRevista());
        return panel;
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtEditorial.setText("");
        cbPeriodicidad.setSelectedIndex(0);
        txtFecha.setText("");
        txtUnidades.setText("");
        txtTitulo.requestFocus();
    }

    private void guardarRevista() {
        try {
            String titulo = txtTitulo.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String periodicidad = (String) cbPeriodicidad.getSelectedItem();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim()); // yyyy-MM-dd
            int unidades = Integer.parseInt(txtUnidades.getText().trim());

            var revista = service.agregarRevista(titulo, editorial, periodicidad, fecha, unidades);
            JOptionPane.showMessageDialog(this,
                    "Revista guardada:\n" + revista,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiar();
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Usa yyyy-MM-dd (ej. 2024-10-01).",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this,
                    "Revisa el campo numérico (Unidades).",
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
