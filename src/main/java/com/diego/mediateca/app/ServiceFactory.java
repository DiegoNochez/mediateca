package com.diego.mediateca.app;

import com.diego.mediateca.repo.MaterialRepository;
import com.diego.mediateca.services.MaterialService;

import java.time.LocalDate;

public final class ServiceFactory {
    private ServiceFactory() {}

   
    private static final MaterialService INSTANCE = build();

    public static MaterialService materialService() {
        return INSTANCE;
    }

    // el service y siembra datos de demo
    private static MaterialService build() {
        MaterialRepository repo = new MaterialRepository();
        MaterialService s = new MaterialService(repo);

        // Los ejemplos 
        s.agregarLibro("Clean Code", "Robert C. Martin", 464, "Prentice Hall",
                "9780132350884", 2008, 5);
        s.agregarLibro("Effective Java", "Joshua Bloch", 416, "Addison-Wesley",
                "9780134685991", 2018, 3);

        s.agregarRevista("IEEE Software", "IEEE", "Mensual",
                LocalDate.of(2025, 10, 1), 4);
        s.agregarRevista("ACM Queue", "ACM", "Mensual",
                LocalDate.of(2025, 9, 15), 2);

        s.agregarCD("Random Access Memories", "Daft Punk", "Electrónica",
                "74:24", 13, 6);
        s.agregarCD("Thriller", "Michael Jackson", "Pop",
                "42:19", 9, 1);

        s.agregarDVD("Inception", "Christopher Nolan", "148 min",
                "Ciencia ficción", 5);
        s.agregarDVD("The Matrix", "Lana & Lilly Wachowski", "136 min",
                "Acción", 2);

        return s;
    }
}
