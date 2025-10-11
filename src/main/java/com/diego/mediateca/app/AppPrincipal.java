package com.diego.mediateca.app;

import com.diego.mediateca.domain.*;
import com.diego.mediateca.services.MaterialService;  // ✔
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Optional;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AppPrincipal extends JFrame {

    private final JPanel content = new JPanel(new CardLayout()); 
    private final MaterialService service = ServiceFactory.materialService(); 

    public AppPrincipal() {
        initComponents();
    }


    private void initComponents() {
        setTitle("Mediateca - Principal");
        setSize(980, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Vistas de "Listar disponibles" por tipo 
        content.add(new ListaPanel("LIBRO"),   "LIBROS");
        content.add(new ListaPanel("REVISTA"), "REVISTAS");
        content.add(new ListaPanel("DVD"),     "DVDS");
        content.add(new ListaPanel("CD"),      "CDS");

        add(content, BorderLayout.CENTER);

        // Menú de navegación requerido 
        crearMenu();

        // Mostrar por defecto LIBROS
        showCard("LIBROS");
    }

    // MENU 6 OPCIONES
   private void crearMenu() {
    JMenuBar mb = new JMenuBar();

    // MENU Disponibles (ver las listas por tipo) 
    JMenu mDisponibles = new JMenu("Disponibles");
    JMenuItem itLibros   = new JMenuItem("Libros");
    JMenuItem itRevistas = new JMenuItem("Revistas");
    JMenuItem itDVDs     = new JMenuItem("DVDs");
    JMenuItem itCDs      = new JMenuItem("CDs");

    itLibros.addActionListener(e -> showCard("LIBROS"));
    itRevistas.addActionListener(e -> showCard("REVISTAS"));
    itDVDs.addActionListener(e -> showCard("DVDS"));
    itCDs.addActionListener(e -> showCard("CDS"));

    mDisponibles.add(itLibros);
    mDisponibles.add(itRevistas);
    mDisponibles.add(itDVDs);
    mDisponibles.add(itCDs);

    // MENU OPERACIONES
    JMenu mOps = new JMenu("Operaciones");

    JMenuItem miAgregar   = new JMenuItem("Agregar material…");
    JMenuItem miModificar = new JMenuItem("Modificar material…");
    JMenuItem miBorrar    = new JMenuItem("Borrar material…");
    JMenuItem miBuscar    = new JMenuItem("Buscar material…");
    JMenuItem miSalir     = new JMenuItem("Salir");

    miAgregar.addActionListener(e -> onAgregarMaterial());
    miModificar.addActionListener(e -> onModificarMaterial());
    miBorrar.addActionListener(e -> onBorrarMaterial());
    miBuscar.addActionListener(e -> onBuscarMaterial());
    miSalir.addActionListener(e -> dispose());

    mOps.add(miAgregar);
    mOps.add(miModificar);
    mOps.add(miBorrar);
    mOps.add(miBuscar);
    mOps.addSeparator();
    mOps.add(miSalir);

    // Orden en la barra
    mb.add(mDisponibles);
    mb.add(mOps);

    setJMenuBar(mb);
}


    // ACCIONES
    private void onAgregarMaterial() {
        String[] tipos = {"LIBRO", "REVISTA", "DVD", "CD"};
        String tipo = (String) JOptionPane.showInputDialog(
                this, "Tipo de material:", "Agregar",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == null) return;

        try {
            switch (tipo) {
                case "LIBRO"   -> agregarLibroDialog();
                case "REVISTA" -> agregarRevistaDialog();
                case "DVD"     -> agregarDVDDialog();
                case "CD"      -> agregarCDDialog();
            }
            JOptionPane.showMessageDialog(this, "Material agregado con éxito.");
            refrescarVistaActual();
        } catch (Exception ex) {
            showError("No se pudo agregar: " + ex.getMessage());
        }
    }

    private void onModificarMaterial() {
        String id = input("ID interno del material a modificar:");
        if (id == null || id.isBlank()) return;

        Optional<Material> op = service.buscarPorId(id);
        if (op.isEmpty()) {
            showWarn("No existe material con ID: " + id);
            return;
        }
        Material m = op.get();

        try {
            if (m instanceof Libro) {
                int u = inputInt("Unidades nuevas para LIBRO " + m.getIdInterno() + ":");
                service.modificarUnidadesLibro(m.getIdInterno(), u);
            } else if (m instanceof Revista) {
                int u = inputInt("Unidades nuevas para REVISTA " + m.getIdInterno() + ":");
                service.modificarUnidadesRevista(m.getIdInterno(), u);
            } else if (m instanceof DVD) {
                String director = input("Director:");
                String dur = input("Duración (hh:mm):");
                String genero = input("Género:");
                int u = inputInt("Unidades disponibles:");
                service.modificarDVD(m.getIdInterno(), director, dur, genero, u);
            } else if (m instanceof CD) {
                String artista = input("Artista:");
                String genero = input("Género:");
                String dur = input("Duración (hh:mm):");
                int num = inputInt("Número de canciones:");
                int u = inputInt("Unidades disponibles:");
                service.modificarCD(m.getIdInterno(), artista, genero, dur, num, u);
            }
            JOptionPane.showMessageDialog(this, "Material modificado.");
            refrescarVistaActual();
        } catch (Exception ex) {
            showError("No se pudo modificar: " + ex.getMessage());
        }
    }

    private void onBorrarMaterial() {
        String id = input("ID interno del material a borrar:");
        if (id == null || id.isBlank()) return;

        Optional<Material> op = service.buscarPorId(id);
        if (op.isEmpty()) {
            showWarn("No existe material con ID: " + id);
            return;
        }

        int ok = JOptionPane.showConfirmDialog(this,
                "¿Borrar definitivamente el material " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        try {
            service.borrarMaterial(id);
            JOptionPane.showMessageDialog(this, "Material borrado.");
            refrescarVistaActual();
        } catch (Exception ex) {
            showError("No se pudo borrar: " + ex.getMessage());
        }
    }

    private void onBuscarMaterial() {
        String id = input("ID interno a buscar:");
        if (id == null || id.isBlank()) return;

        Optional<Material> op = service.buscarPorId(id);
        if (op.isEmpty()) {
            showWarn("No existe material con ID: " + id);
            return;
        }
        Material m = op.get();
        JOptionPane.showMessageDialog(this, detalleMaterial(m), "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }

    //   Helpers UI 
    private void showCard(String name) {
        ((CardLayout) content.getLayout()).show(content, name);
    }

    private void refrescarVistaActual() {
        // fuerza un "Listar" de la tarjeta visible (si el panel lo implementa)
        Component visible = null;
        for (Component comp : content.getComponents()) {
            if (comp.isVisible()) { visible = comp; break; }
        }
        if (visible instanceof ListaPanel lp) {
            lp.refrescar();
        }
    }

    private static String input(String msg) {
        return JOptionPane.showInputDialog(null, msg);
    }

    private static int inputInt(String msg) {
        String s = JOptionPane.showInputDialog(null, msg);
        if (s == null) throw new IllegalArgumentException("Acción cancelada");
        return Integer.parseInt(s.trim());
    }

    private static void showWarn(String msg) { JOptionPane.showMessageDialog(null, msg, "Aviso", JOptionPane.WARNING_MESSAGE); }
    private static void showError(String msg){ JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE); }

    private static String detalleMaterial(Material m) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(m.getIdInterno()).append('\n')
          .append("Título: ").append(m.getTitulo()).append('\n')
          .append("Unidades: ").append(m.getUnidadesDisponibles()).append('\n');

        if (m instanceof Libro l) {
            sb.append("Autor: ").append(l.getAutor()).append('\n')
              .append("Editorial: ").append(l.getEditorial()).append('\n')
              .append("ISBN: ").append(l.getIsbn()).append('\n')
              .append("Año: ").append(l.getAnioPublicacion());
        } else if (m instanceof Revista r) {
            sb.append("Editorial: ").append(r.getEditorial()).append('\n')
              .append("Periodicidad: ").append(r.getPeriodicidad()).append('\n')
              .append("Fecha publicación: ").append(r.getFechaPublicacion());
        } else if (m instanceof DVD d) {
            sb.append("Director: ").append(d.getDirector()).append('\n')
              .append("Duración: ").append(d.getDuracion()).append('\n')
              .append("Género: ").append(d.getGenero());
        } else if (m instanceof CD c) {
            sb.append("Artista: ").append(c.getArtista()).append('\n')
              .append("Género: ").append(c.getGenero()).append('\n')
              .append("Duración: ").append(c.getDuracion()).append('\n')
              .append("# Canciones: ").append(c.getNumeroCanciones());
        }
        return sb.toString();
    }

    // Diálogos de alta 
    private void agregarLibroDialog() {
        String titulo = input("Título:");
        String autor = input("Autor:");
        int paginas = inputInt("Número de páginas:");
        String editorial = input("Editorial:");
        String isbn = input("ISBN:");
        int anio = inputInt("Año de publicación:");
        int unidades = inputInt("Unidades disponibles:");
        service.agregarLibro(titulo, autor, paginas, editorial, isbn, anio, unidades);
    }

    private void agregarRevistaDialog() {
        String titulo = input("Título:");
        String editorial = input("Editorial:");
        String periodicidad = input("Periodicidad (Mensual/Semanal/etc):");
        String fecha = input("Fecha publicación (YYYY-MM-DD):");
        int unidades = inputInt("Unidades disponibles:");
        service.agregarRevista(titulo, editorial, periodicidad, LocalDate.parse(fecha), unidades);
    }

    private void agregarDVDDialog() {
        String titulo = input("Título:");
        String director = input("Director:");
        String duracion = input("Duración (hh:mm):");
        String genero = input("Género:");
        int unidades = inputInt("Unidades disponibles:");
        service.agregarDVD(titulo, director, duracion, genero, unidades);
    }

    private void agregarCDDialog() {
        String titulo = input("Título:");
        String artista = input("Artista:");
        String genero = input("Género:");
        String duracion = input("Duración (hh:mm):");
        int num = inputInt("Número de canciones:");
        int unidades = inputInt("Unidades disponibles:");
        service.agregarCD(titulo, artista, genero, duracion, num, unidades);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppPrincipal().setVisible(true));
    }
}
