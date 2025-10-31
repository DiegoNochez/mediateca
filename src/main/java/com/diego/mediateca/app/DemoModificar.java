package com.diego.mediateca.app;

import java.sql.SQLException;

import com.diego.mediateca.db.DatabaseConnection;
import com.diego.mediateca.db.MaterialDAO;
import com.diego.mediateca.domain.CD;
import com.diego.mediateca.domain.DVD;
import com.diego.mediateca.domain.Libro;

/**
 * Programa de demostración para probar las funcionalidades de modificación
 */
public class DemoModificar {
    
    public static void main(String[] args) {
        try {
            System.out.println("=".repeat(60));
            System.out.println("DEMO: MODIFICAR MATERIALES - MEDIATECA");
            System.out.println("=".repeat(60));
            
            // 1. Probar conexión a BD
            probarConexion();
            
            // 2. Insertar datos de prueba
            insertarDatosPrueba();
            
            // 3. Probar modificaciones
            probarModificaciones();
            
            System.out.println("\n✓ Demo completada exitosamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error en demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void probarConexion() {
        System.out.println("\n[1] Probando conexión a base de datos...");
        DatabaseConnection db = DatabaseConnection.getInstance();
        if (db.testConnection()) {
            System.out.println("✓ Conexión exitosa");
        } else {
            System.out.println("❌ Error en conexión");
        }
    }
    
    private static void insertarDatosPrueba() throws SQLException {
        System.out.println("\n[2] Insertando datos de prueba...");
        MaterialDAO dao = new MaterialDAO();
        
        // Insertar un CD
        CD cd1 = new CD("CDA00001", "Abbey Road", 5, 
                        "The Beatles", "Rock", "47:23", 17);
        if (dao.insertarCD(cd1)) {
            System.out.println("✓ CD insertado: " + cd1.getIdInterno());
        }
        
        // Insertar un DVD
        DVD dvd1 = new DVD("DVD00001", "Inception", 3, 
                           "Christopher Nolan", "148:00", "Ciencia Ficción");
        if (dao.insertarDVD(dvd1)) {
            System.out.println("✓ DVD insertado: " + dvd1.getIdInterno());
        }
        
        // Insertar un Libro
        Libro libro1 = new Libro("LIB00001", "Clean Code", 
                                 "Robert C. Martin", 464, 
                                 "Prentice Hall", "978-0132350884", 
                                 2008, 10);
        if (dao.insertarLibro(libro1)) {
            System.out.println("✓ Libro insertado: " + libro1.getIdInterno());
        }
    }
    
    private static void probarModificaciones() throws SQLException {
        System.out.println("\n[3] Probando modificaciones...");
        MaterialDAO dao = new MaterialDAO();
        
        // Modificar CD
        System.out.println("\n--- Modificando CD ---");
        System.out.println("Antes de modificar:");
        var cdAntes = dao.buscarPorId("CDA00001");
        cdAntes.ifPresent(System.out::println);
        
        dao.modificarCD("CDA00001", "The Beatles", "Rock Clásico", 17, 8);
        
        System.out.println("\nDespués de modificar:");
        var cdDespues = dao.buscarPorId("CDA00001");
        cdDespues.ifPresent(System.out::println);
        System.out.println("✓ CD modificado correctamente");
        
        // Modificar DVD
        System.out.println("\n--- Modificando DVD ---");
        System.out.println("Antes de modificar:");
        var dvdAntes = dao.buscarPorId("DVD00001");
        dvdAntes.ifPresent(System.out::println);
        
        // Only update director and unidades (DAO supports id, director, unidades)
        dao.modificarDVD("DVD00001", "Christopher Nolan", 10);
        
        System.out.println("\nDespués de modificar:");
        var dvdDespues = dao.buscarPorId("DVD00001");
        dvdDespues.ifPresent(System.out::println);
        System.out.println("✓ DVD modificado correctamente");
        
        // Modificar Libro (solo unidades)
        System.out.println("\n--- Modificando Libro ---");
        System.out.println("Antes de modificar:");
        var libroAntes = dao.buscarPorId("LIB00001");
        libroAntes.ifPresent(System.out::println);
        
        dao.modificarLibro("LIB00001", 15);
        
        System.out.println("\nDespués de modificar:");
        var libroDespues = dao.buscarPorId("LIB00001");
        libroDespues.ifPresent(System.out::println);
        System.out.println("✓ Libro modificado correctamente");
    }
}
