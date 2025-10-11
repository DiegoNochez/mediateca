// src/main/java/com/diego/mediateca/repo/MaterialRepository.java
package com.diego.mediateca.repo;

import com.diego.mediateca.db.MaterialDAO;
import com.diego.mediateca.domain.CD;
import com.diego.mediateca.domain.DVD;
import com.diego.mediateca.domain.Libro;
import com.diego.mediateca.domain.Material;
import com.diego.mediateca.domain.Revista;

import java.sql.SQLException;
import java.util.*;

/**
 * Repositorio híbrido:
 * - Si se construye con DAO, las lecturas de "disponibles" van a BD (JDBC).
 * - El resto (save/update/findAll/etc.) siguen usando el mapa en memoria (tu flujo actual).
 */
public class MaterialRepository {

    // --- En memoria (lo que ya tenías) ---
    private final Map<String, Material> porId = new HashMap<>();

    // --- JDBC (opcional) ---
    private final MaterialDAO dao; // si es null, no hay BD

    /** Repositorio en memoria (sin BD) */
    public MaterialRepository() {
        this.dao = null;
    }

    /** Repositorio respaldado por JDBC */
    public MaterialRepository(MaterialDAO dao) {
        this.dao = dao;
    }

    // -------------------- CRUD en memoria (sin cambios) --------------------

    public boolean exists(String idInterno) {
        return porId.containsKey(idInterno);
    }

    public void save(Material material) {
        if (exists(material.getIdInterno())) {
            throw new IllegalStateException("Ya existe un material con id " + material.getIdInterno());
        }
        porId.put(material.getIdInterno(), material);
    }

    public void update(Material material) {
        if (!exists(material.getIdInterno())) {
            throw new IllegalStateException("No existe un material con id " + material.getIdInterno());
        }
        porId.put(material.getIdInterno(), material);
    }

    public Optional<Material> findById(String id) {
        return Optional.ofNullable(porId.get(id));
    }

    public List<Material> findAll() {
        return new ArrayList<>(porId.values());
    }

    public void delete(String idInterno) {
        if (!exists(idInterno)) {
            throw new IllegalStateException("No existe un material con id " + idInterno);
        }
        porId.remove(idInterno);
    }

    // -------------------- Lectura "disponibles por tipo" --------------------

    /**
     * Devuelve SOLO los materiales con unidades > 0 del tipo indicado.
     * Si hay DAO, consulta BD; si no, filtra la colección en memoria.
     * Tipos válidos: "LIBRO", "REVISTA", "DVD", "CD"
     */
    public List<Material> findDisponiblesPorTipo(String tipo) {
        String t = (tipo == null) ? "" : tipo.toUpperCase();

        // 1) Conexión JDBC disponible → leer de BD
        if (dao != null) {
            try {
                List<Material> out = new ArrayList<>();
                switch (t) {
                    case "LIBRO": {
                        List<Libro> libros = dao.listarLibrosDisponibles();
                        out.addAll(libros);
                        return out;
                    }
                    case "REVISTA": {
                        List<Revista> revistas = dao.listarRevistasDisponibles();
                        out.addAll(revistas);
                        return out;
                    }
                    case "DVD": {
                        List<DVD> dvds = dao.listarDVDsDisponibles();
                        out.addAll(dvds);
                        return out;
                    }
                    case "CD": {
                        List<CD> cds = dao.listarCDsDisponibles();
                        out.addAll(cds);
                        return out;
                    }
                    default:
                        return Collections.emptyList();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error consultando materiales disponibles en BD", e);
            }
        }

        // 2) Sin BD → filtrar lo que haya en memoria
        List<Material> todos = findAll();
        List<Material> out = new ArrayList<>();
        for (Material m : todos) {
            if (m.getUnidadesDisponibles() <= 0) continue;
            switch (t) {
                case "LIBRO":
                    if (m instanceof Libro) out.add(m);
                    break;
                case "REVISTA":
                    if (m instanceof Revista) out.add(m);
                    break;
                case "DVD":
                    if (m instanceof DVD) out.add(m);
                    break;
                case "CD":
                    if (m instanceof CD) out.add(m);
                    break;
                default:
                    // tipo desconocido -> vacío
            }
        }
        return out;
    }
}
