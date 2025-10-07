package com.diego.mediateca.services;

import com.diego.mediateca.domain.*;
import com.diego.mediateca.repo.MaterialRepository;
import java.time.LocalDate;

public class MaterialService {
    private final MaterialRepository repo;

    public MaterialService(MaterialRepository repo) {
        this.repo = repo;
    }

    public Libro agregarLibro(String titulo, String autor, int paginas, String editorial,
                              String isbn, int anioPublicacion, int unidades) {
        String id = CodigoInternoGenerator.next("LIB");
        Libro libro = new Libro(id, titulo, autor, paginas, editorial, isbn, anioPublicacion, unidades);
        repo.save(libro);
        return libro;
    }

    public Revista agregarRevista(String titulo, String editorial, String periodicidad,
                                  LocalDate fechaPublicacion, int unidades) {
        String id = CodigoInternoGenerator.next("REV");
        Revista revista = new Revista(id, titulo, editorial, periodicidad, fechaPublicacion, unidades);
        repo.save(revista);
        return revista;
    }
}
