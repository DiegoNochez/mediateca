package com.diego.mediateca.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            // Cargar el driver de MySQL
            Class.forName(DatabaseConfig.DB_DRIVER);
            
            // Establecer conexión
            this.connection = DriverManager.getConnection(
                DatabaseConfig.DB_URL,
                DatabaseConfig.DB_USER,
                DatabaseConfig.DB_PASSWORD
            );
            
            System.out.println("✓ Conexión a base de datos establecida exitosamente");
            
            // Crear las tablas si no existen
            crearTablasIniciales();
            
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error: Driver de MySQL no encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la instancia única de DatabaseConnection (Patrón Singleton)
     */
    public static DatabaseConnection getInstance() {
        if (instance == null || !isConnectionValid()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Obtiene la conexión a la base de datos
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Reconectar si la conexión está cerrada
                connection = DriverManager.getConnection(
                    DatabaseConfig.DB_URL,
                    DatabaseConfig.DB_USER,
                    DatabaseConfig.DB_PASSWORD
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión: " + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Verifica si la conexión es válida
     */
    private static boolean isConnectionValid() {
        try {
            return instance != null && 
                   instance.connection != null && 
                   !instance.connection.isClosed() &&
                   instance.connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Crea las tablas iniciales si no existen
     */
    private void crearTablasIniciales() {
        try (Statement stmt = connection.createStatement()) {
            
            // Crear tabla de libros
            stmt.execute(DatabaseConfig.CREATE_TABLE_LIBROS);
            System.out.println("✓ Tabla 'libros' verificada/creada");
            
            // Crear tabla de revistas
            stmt.execute(DatabaseConfig.CREATE_TABLE_REVISTAS);
            System.out.println("✓ Tabla 'revistas' verificada/creada");
            
            // Crear tabla de CDs
            stmt.execute(DatabaseConfig.CREATE_TABLE_CDS);
            System.out.println("✓ Tabla 'cds' verificada/creada");
            
            // Crear tabla de DVDs
            stmt.execute(DatabaseConfig.CREATE_TABLE_DVDS);
            System.out.println("✓ Tabla 'dvds' verificada/creada");
            
        } catch (SQLException e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    /**
     * Ejecuta una consulta de prueba para verificar la conexión
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Conectado a: " + metaData.getDatabaseProductName() + 
                             " versión " + metaData.getDatabaseProductVersion());
            return true;
        } catch (SQLException e) {
            System.err.println("Error en test de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método auxiliar para cerrar recursos JDBC de forma segura
     */
    public static void cerrarRecursos(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar ResultSet: " + e.getMessage());
        }
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para cerrar Statement
     */
    public static void cerrarStatement(Statement stmt) {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar Statement: " + e.getMessage());
        }
    }
}
