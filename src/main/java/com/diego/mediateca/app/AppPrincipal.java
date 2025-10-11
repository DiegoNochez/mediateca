package com.diego.mediateca.app;

import javax.swing.*;
import java.awt.*;

public class AppPrincipal extends JFrame {

    public AppPrincipal() {
        initComponents();
    }

    private void initComponents() {
       
        setTitle("Mediateca - Principal");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Layout principal
        setLayout(new BorderLayout());

        // Panel central con CardLayout
        JPanel content = new JPanel(new CardLayout());

        // Paneles de prueba 
        JPanel panelLibros   = new JPanel(); panelLibros.add(new JLabel("📚 Libros disponibles"));
        JPanel panelRevistas = new JPanel(); panelRevistas.add(new JLabel("📰 Revistas disponibles"));
        JPanel panelDVDs     = new JPanel(); panelDVDs.add(new JLabel("🎬 DVDs disponibles"));
        JPanel panelCDs      = new JPanel(); panelCDs.add(new JLabel("💿 CDs disponibles"));

        // Registrar las "tarjetas" con nombres
        content.add(panelLibros,   "LIBROS");
        content.add(panelRevistas, "REVISTAS");
        content.add(panelDVDs,     "DVDS");
        content.add(panelCDs,      "CDS");

        // Borde temporal solo para ver el área
        content.setBorder(BorderFactory.createTitledBorder("Área de contenido"));

        // Agregamos el panel al centro
        add(content, BorderLayout.CENTER);

       
        crearMenu(content);
    }

    // ----- Menú de navegación -----
    private void crearMenu(JPanel content) {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu mArchivo = new JMenu("Archivo");
        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(e -> dispose());
        mArchivo.add(miSalir);

        // Menú Materiales
        JMenu mMateriales = new JMenu("Materiales");
        JMenuItem miLibros   = new JMenuItem("Libros");
        JMenuItem miRevistas = new JMenuItem("Revistas");
        JMenuItem miDVDs     = new JMenuItem("DVDs");
        JMenuItem miCDs      = new JMenuItem("CDs");

        // Acciones: cambiar tarjeta del CardLayout
        miLibros.addActionListener(e   -> mostrarVista(content, "LIBROS"));
        miRevistas.addActionListener(e -> mostrarVista(content, "REVISTAS"));
        miDVDs.addActionListener(e     -> mostrarVista(content, "DVDS"));
        miCDs.addActionListener(e      -> mostrarVista(content, "CDS"));

        mMateriales.add(miLibros);
        mMateriales.add(miRevistas);
        mMateriales.add(miDVDs);
        mMateriales.add(miCDs);

        // Agregar menús a la barra y asignarla al frame
        menuBar.add(mArchivo);
        menuBar.add(mMateriales);
        setJMenuBar(menuBar);
    }

    // Cambia la vista activa en el panel central
    private void mostrarVista(JPanel content, String nombre) {
        CardLayout cl = (CardLayout) content.getLayout();
        cl.show(content, nombre);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppPrincipal().setVisible(true));
    }
}
