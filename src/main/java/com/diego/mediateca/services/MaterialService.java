
package com.diego.mediateca.services;

import java.time.LocalDate;
import java.util.List;

import com.diego.mediateca.domain.CD;
import com.diego.mediateca.domain.DVD;
import com.diego.mediateca.domain.Libro;
import com.diego.mediateca.domain.Material;
import com.diego.mediateca.domain.Revista;
import com.diego.mediateca.repo.MaterialRepository;

public class MaterialService {
    private final MaterialRepository repo;

    public MaterialService(MaterialRepository repo) {
        this.repo = repo;
    }

    // ---------- Agregar ----------
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

    public CD agregarCD(String titulo, String artista, String genero,
                        String duracion, int numeroCanciones, int unidades) {
        String id = CodigoInternoGenerator.next("CDA");
        CD cd = new CD(id, titulo, unidades, artista, genero, duracion, numeroCanciones);
        repo.save(cd);
        return cd;
    }

    public DVD agregarDVD(String titulo, String director, String duracion,
                          String genero, int unidades) {
        String id = CodigoInternoGenerator.next("DVD");
        DVD dvd = new DVD(id, titulo, unidades, director, duracion, genero);
        repo.save(dvd);
        return dvd;
    }


    public List<Material> listarDisponiblesPorTipo(String tipo) {
        return repo.findDisponiblesPorTipo(tipo);
    }

    // ---------- Modificar ----------
    /** Modifica las unidades disponibles de cualquier material */
    public void modificarUnidades(String idInterno, int nuevasUnidades) {
        Material material = repo.findById(idInterno)
                .orElseThrow(() -> new IllegalArgumentException("No existe material con ID: " + idInterno));
        material.setUnidadesDisponibles(nuevasUnidades);
        repo.update(material);
    }

    /** Modifica un CD de audio */
    public void modificarCD(String idInterno, String artista, String genero,
                            String duracion, int numeroCanciones, int unidades) {
        Material material = repo.findById(idInterno)
                .orElseThrow(() -> new IllegalArgumentException("No existe material con ID: " + idInterno));

        if (!(material instanceof CD)) {
            throw new IllegalArgumentException("El ID " + idInterno + " no corresponde a un CD");
        }

        CD cd = (CD) material;
        cd.setArtista(artista);
        cd.setGenero(genero);
        cd.setDuracion(duracion);
        cd.setNumeroCanciones(numeroCanciones);
        cd.setUnidadesDisponibles(unidades);
        repo.update(cd);
    }

    
    public void modificarDVD(String idInterno, String director, String duracion,
                             String genero, int unidades) {
        Material material = repo.findById(idInterno)
                .orElseThrow(() -> new IllegalArgumentException("No existe material con ID: " + idInterno));

        if (!(material instanceof DVD)) {
            throw new IllegalArgumentException("El ID " + idInterno + " no corresponde a un DVD");
        }

        DVD dvd = (DVD) material;
        dvd.setDirector(director);
        dvd.setDuracion(duracion);
        dvd.setGenero(genero);
        dvd.setUnidadesDisponibles(unidades);
        repo.update(dvd);
    }

    public void modificarUnidadesLibro(String idInterno, int unidades) {
        Material material = repo.findById(idInterno)
                .orElseThrow(() -> new IllegalArgumentException("No existe material con ID: " + idInterno));

        if (!(material instanceof Libro)) {
            throw new IllegalArgumentException("El ID " + idInterno + " no corresponde a un Libro");
        }

        material.setUnidadesDisponibles(unidades);
        repo.update(material);
    }

    public void modificarUnidadesRevista(String idInterno, int unidades) {
        Material material = repo.findById(idInterno)
                .orElseThrow(() -> new IllegalArgumentException("No existe material con ID: " + idInterno));

        if (!(material instanceof Revista)) {
            throw new IllegalArgumentException("El ID " + idInterno + " no corresponde a una Revista");
        }

        material.setUnidadesDisponibles(unidades);
        repo.update(material);
    }
}
