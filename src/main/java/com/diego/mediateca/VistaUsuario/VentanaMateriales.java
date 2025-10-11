package com.diego.mediateca.VistaUsuario;

import com.diego.mediateca.repo.MaterialRepository;
import com.diego.mediateca.services.MaterialService;

import javax.swing.*;
import java.awt.*;

public class VentanaMateriales extends JFrame {
    public VentanaMateriales() {
        super("Mediateca - Materiales (Libro/Revista)");

        var repo = new MaterialRepository();
        var service = new MaterialService(repo);

        var tabs = new JTabbedPane();
        tabs.add("Libro", new LibroPanel(service));
        tabs.add("Revista", new RevistaPanel(service));

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaMateriales().setVisible(true));
    }
}
