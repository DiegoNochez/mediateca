package com.diego.mediateca.domain;

public class DVD extends Material {
    private String director;
    private String duracion;
    private String genero;

    public DVD(String idInterno, String titulo, int unidadesDisponibles,
               String director, String duracion, String genero) {
        super(idInterno, titulo, unidadesDisponibles);

        if (director == null || director.isBlank()) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        if (duracion == null || duracion.isBlank()) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("El género es obligatorio.");
        }

        this.director = director.trim();
        this.duracion = duracion.trim();
        this.genero = genero.trim();
    }

    //getters
    public String getDirector() {
        return director;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    //setters
    public void setDirector(String director) {
        if (director == null || director.isBlank()) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        this.director = director.trim();
    }

    public void setDuracion(String duracion) {
        if (duracion == null || duracion.isBlank()) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }
        this.duracion = duracion.trim();
    }

    public void setGenero(String genero) {
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("El género es obligatorio.");
        }
        this.genero = genero.trim();
    }

    @Override
    public String tipo() {
        return "DVD";
    }

    @Override
    public String toString() {
        return "DVD{id='%s', titulo='%s', director='%s', duracion='%s', genero='%s', unidades=%d}"
                .formatted(idInterno, titulo, director, duracion, genero, unidadesDisponibles);
    }
}
