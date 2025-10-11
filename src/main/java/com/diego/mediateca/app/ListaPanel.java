package com.diego.mediateca.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.diego.mediateca.services.MaterialService;

public class ListaPanel extends JPanel {

    private final String tipo;          
    private final MaterialService service = ServiceFactory.materialService();

    private JTextField txtBuscar;
    private JTable tabla;

    public ListaPanel(String tipo) {
        this.tipo = tipo;
        buildUI();
        cargarDatos("");
    }

    private void buildUI() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(25);
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarDatos(txtBuscar.getText().trim()));
        top.add(txtBuscar);
        top.add(btnRefrescar);

        add(top, BorderLayout.NORTH);

        tabla = new JTable();
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JLabel lbl = new JLabel("Disponibles: " + tipo, SwingConstants.LEFT);
        add(lbl, BorderLayout.SOUTH);
    }

    private void cargarDatos(String filtro) {
        List<?> data = (filtro == null || filtro.isEmpty())
                ? service.listar(tipo)
                : service.buscar(tipo, filtro);

        MaterialTableModel tableModel = MaterialTableModel.of(tipo, data);
        tabla.setModel(tableModel);
        tabla.setAutoCreateRowSorter(true);
    }

    public void refrescar() {
        cargarDatos(txtBuscar.getText().trim());
    }
}
