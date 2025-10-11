package com.diego.mediateca.app;

import com.diego.mediateca.db.MaterialDAO;
import com.diego.mediateca.repo.MaterialRepository;
import com.diego.mediateca.services.MaterialService;

public final class ServiceFactory {
    private ServiceFactory() {}

    public static MaterialService materialService() {
      
        MaterialDAO dao = new MaterialDAO();
        MaterialRepository repo = new MaterialRepository(dao);
        return new MaterialService(repo);
    }
}
