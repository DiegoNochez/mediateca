package com.diego.mediateca.domain;

public class CD extends Material {
    private String artista;
    private String genero;
    private String duracion;
    private int numeroCanciones;

    public CD(String idInterno, String titulo, int unidadesDisponibles,
              String artista, String genero, String duracion, int numeroCanciones) {
        super(idInterno, titulo, unidadesDisponibles);

        if (artista == null || artista.isBlank()) {
            throw new IllegalArgumentException("El artista es obligatorio.");
        }
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("El género es obligatorio.");
        }
        if (duracion == null || duracion.isBlank()) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }
        if (numeroCanciones <= 0) {
            throw new IllegalArgumentException("El número de canciones debe ser mayor a cero.");
        }

        this.artista = artista.trim();
        this.genero = genero.trim();
        this.duracion = duracion.trim();
        this.numeroCanciones = numeroCanciones;
    }

    //getters
    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    //setters
    public void setArtista(String artista) {
        if (artista == null || artista.isBlank()) {
            throw new IllegalArgumentException("El artista es obligatorio.");
        }
        this.artista = artista.trim();
    }

    public void setGenero(String genero) {
        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException("El género es obligatorio.");
        }
        this.genero = genero.trim();
    }

    public void setDuracion(String duracion) {
        if (duracion == null || duracion.isBlank()) {
            throw new IllegalArgumentException("La duración es obligatoria.");
        }
        this.duracion = duracion.trim();
    }

    public void setNumeroCanciones(int numeroCanciones) {
        if (numeroCanciones <= 0) {
            throw new IllegalArgumentException("El número de canciones debe ser mayor a cero.");
        }
        this.numeroCanciones = numeroCanciones;
    }

    @Override
    public String tipo() {
        return "CD";
    }

    @Override
    public String toString() {
        return "CD{id='%s', titulo='%s', artista='%s', genero='%s', duracion='%s', canciones=%d, unidades=%d}"
                .formatted(idInterno, titulo, artista, genero, duracion, numeroCanciones, unidadesDisponibles);
    }
}