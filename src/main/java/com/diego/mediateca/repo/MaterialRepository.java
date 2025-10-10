package com.diego.mediateca.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.diego.mediateca.domain.Material;

public class MaterialRepository {
    private final Map<String, Material> porId = new HashMap<>();

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
}