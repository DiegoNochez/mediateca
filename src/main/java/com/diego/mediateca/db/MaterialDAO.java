package com.diego.mediateca.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.diego.mediateca.domain.CD;
import com.diego.mediateca.domain.DVD;
import com.diego.mediateca.domain.Libro;
import com.diego.mediateca.domain.Material;
import com.diego.mediateca.domain.Revista;

/**
 * Data Access Object para gestionar operaciones CRUD de materiales en BD
 */
public class MaterialDAO {
    
    private final DatabaseConnection dbConnection;

    public MaterialDAO() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    // ==================== MÉTODOS DE MODIFICACIÓN (TU PARTE PRINCIPAL) ====================
    
    /**
     * Modifica un libro en la base de datos
     * Nota: Solo se pueden modificar unidades disponibles (campos son final)
     */
    public boolean modificarLibro(String idInterno, int unidadesDisponibles) throws SQLException {
        String sql = "UPDATE libros SET unidades_disponibles = ? WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, unidadesDisponibles);
            ps.setString(2, idInterno);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Modifica una revista en la base de datos
     * Nota: Solo se pueden modificar unidades disponibles (campos son final)
     */
    public boolean modificarRevista(String idInterno, int unidadesDisponibles) throws SQLException {
        String sql = "UPDATE revistas SET unidades_disponibles = ? WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, unidadesDisponibles);
            ps.setString(2, idInterno);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Modifica un CD en la base de datos
     */
    public boolean modificarCD(String idInterno, String artista, String genero, 
                               String duracion, int numeroCanciones, int unidadesDisponibles) 
                               throws SQLException {
        String sql = """
            UPDATE cds SET 
                artista = ?, 
                genero = ?, 
                duracion = ?, 
                numero_canciones = ?, 
                unidades_disponibles = ?
            WHERE id_interno = ?
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, artista);
            ps.setString(2, genero);
            ps.setString(3, duracion);
            ps.setInt(4, numeroCanciones);
            ps.setInt(5, unidadesDisponibles);
            ps.setString(6, idInterno);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Modifica un DVD en la base de datos
     */
    public boolean modificarDVD(String idInterno, String director, String duracion, 
                                String genero, int unidadesDisponibles) throws SQLException {
        String sql = """
            UPDATE dvds SET 
                director = ?, 
                duracion = ?, 
                genero = ?, 
                unidades_disponibles = ?
            WHERE id_interno = ?
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, director);
            ps.setString(2, duracion);
            ps.setString(3, genero);
            ps.setInt(4, unidadesDisponibles);
            ps.setString(5, idInterno);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ==================== MÉTODOS DE INSERCIÓN (AGREGAR) ====================
    
    public boolean insertarLibro(Libro libro) throws SQLException {
        String sql = """
            INSERT INTO libros (id_interno, titulo, autor, numero_paginas, editorial, 
                               isbn, anio_publicacion, unidades_disponibles)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, libro.getIdInterno());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setInt(4, libro.getNumeroPaginas());
            ps.setString(5, libro.getEditorial());
            ps.setString(6, libro.getIsbn());
            ps.setInt(7, libro.getAnioPublicacion());
            ps.setInt(8, libro.getUnidadesDisponibles());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean insertarRevista(Revista revista) throws SQLException {
        String sql = """
            INSERT INTO revistas (id_interno, titulo, editorial, periodicidad, 
                                 fecha_publicacion, unidades_disponibles)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, revista.getIdInterno());
            ps.setString(2, revista.getTitulo());
            ps.setString(3, revista.getEditorial());
            ps.setString(4, revista.getPeriodicidad());
            ps.setDate(5, Date.valueOf(revista.getFechaPublicacion()));
            ps.setInt(6, revista.getUnidadesDisponibles());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean insertarCD(CD cd) throws SQLException {
        String sql = """
            INSERT INTO cds (id_interno, titulo, artista, genero, duracion, 
                           numero_canciones, unidades_disponibles)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, cd.getIdInterno());
            ps.setString(2, cd.getTitulo());
            ps.setString(3, cd.getArtista());
            ps.setString(4, cd.getGenero());
            ps.setString(5, cd.getDuracion());
            ps.setInt(6, cd.getNumeroCanciones());
            ps.setInt(7, cd.getUnidadesDisponibles());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    public boolean insertarDVD(DVD dvd) throws SQLException {
        String sql = """
            INSERT INTO dvds (id_interno, titulo, director, duracion, genero, 
                            unidades_disponibles)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, dvd.getIdInterno());
            ps.setString(2, dvd.getTitulo());
            ps.setString(3, dvd.getDirector());
            ps.setString(4, dvd.getDuracion());
            ps.setString(5, dvd.getGenero());
            ps.setInt(6, dvd.getUnidadesDisponibles());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    // ==================== MÉTODOS DE BÚSQUEDA ====================
    
    public Optional<Material> buscarPorId(String idInterno) throws SQLException {
        String tipo = identificarTipo(idInterno);
        
        return switch (tipo) {
            case "LIB" -> buscarLibro(idInterno);
            case "REV" -> buscarRevista(idInterno);
            case "CDA" -> buscarCD(idInterno);
            case "DVD" -> buscarDVDPorId(idInterno);
            default -> Optional.empty();
        };
    }

    private Optional<Material> buscarLibro(String idInterno) throws SQLException {
        String sql = "SELECT * FROM libros WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, idInterno);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("id_interno"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("numero_paginas"),
                    rs.getString("editorial"),
                    rs.getString("isbn"),
                    rs.getInt("anio_publicacion"),
                    rs.getInt("unidades_disponibles")
                );
                return Optional.of(libro);
            }
        }
        return Optional.empty();
    }

    private Optional<Material> buscarRevista(String idInterno) throws SQLException {
        String sql = "SELECT * FROM revistas WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, idInterno);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Revista revista = new Revista(
                    rs.getString("id_interno"),
                    rs.getString("titulo"),
                    rs.getString("editorial"),
                    rs.getString("periodicidad"),
                    rs.getDate("fecha_publicacion").toLocalDate(),
                    rs.getInt("unidades_disponibles")
                );
                return Optional.of(revista);
            }
        }
        return Optional.empty();
    }

    private Optional<Material> buscarCD(String idInterno) throws SQLException {
        String sql = "SELECT * FROM cds WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, idInterno);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                CD cd = new CD(
                    rs.getString("id_interno"),
                    rs.getString("titulo"),
                    rs.getInt("unidades_disponibles"),
                    rs.getString("artista"),
                    rs.getString("genero"),
                    rs.getString("duracion"),
                    rs.getInt("numero_canciones")
                );
                return Optional.of(cd);
            }
        }
        return Optional.empty();
    }

    private Optional<Material> buscarDVDPorId(String idInterno) throws SQLException {
        String sql = "SELECT * FROM dvds WHERE id_interno = ?";
        
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, idInterno);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                DVD dvd = new DVD(
                    rs.getString("id_interno"),
                    rs.getString("titulo"),
                    rs.getInt("unidades_disponibles"),
                    rs.getString("director"),
                    rs.getString("duracion"),
                    rs.getString("genero")
                );
                return Optional.of(dvd);
            }
        }
        return Optional.empty();
    }

    // ==================== MÉTODOS AUXILIARES ====================
    
    private String identificarTipo(String idInterno) {
        if (idInterno.startsWith("LIB")) return "LIB";
        if (idInterno.startsWith("REV")) return "REV";
        if (idInterno.startsWith("CDA")) return "CDA";
        if (idInterno.startsWith("DVD")) return "DVD";
        throw new IllegalArgumentException("Código interno inválido: " + idInterno);
    }

    public boolean existeMaterial(String idInterno) throws SQLException {
        String tipo = identificarTipo(idInterno);
        String tabla = switch (tipo) {
            case "LIB" -> "libros";
            case "REV" -> "revistas";
            case "CDA" -> "cds";
            case "DVD" -> "dvds";
            default -> throw new IllegalArgumentException("Tipo desconocido");
        };
        
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE id_interno = ?";
        try (PreparedStatement ps = dbConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, idInterno);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}