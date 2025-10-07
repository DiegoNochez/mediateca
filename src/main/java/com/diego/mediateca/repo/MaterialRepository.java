package com.diego.mediateca.repo;

import com.diego.mediateca.domain.Material;
import java.util.*;

public class MaterialRepository {
    private final Map<String, Material> porId = new HashMap<>();

    public boolean exists(String idInterno) { return porId.containsKey(idInterno); }

    public void save(Material material) {
        if (exists(material.getIdInterno())) {
            throw new IllegalStateException("Ya existe un material con id " + material.getIdInterno());
        }
        porId.put(material.getIdInterno(), material);
    }

    public Optional<Material> findById(String id) { return Optional.ofNullable(porId.get(id)); }

    public List<Material> findAll() { return new ArrayList<>(porId.values()); }
}
