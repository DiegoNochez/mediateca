// src/main/java/com/diego/mediateca/app/ServiceFactory.java
package com.diego.mediateca.app;

import com.diego.mediateca.db.MaterialDAO;
import com.diego.mediateca.repo.MaterialRepository;
import com.diego.mediateca.services.MaterialService;

public final class ServiceFactory {
    private ServiceFactory() {}

    public static MaterialService materialService() {
        // ===== OPCIÓN JDBC (recomendada si Persona 3 ya hizo BD) =====
        MaterialDAO dao = new MaterialDAO();
        MaterialRepository repo = new MaterialRepository(dao);
        return new MaterialService(repo);

        // ===== OPCIÓN EN MEMORIA (fallback) =====
        // MaterialRepository repo = new MaterialRepository();
        // return new MaterialService(repo);
    }
}
