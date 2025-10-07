package com.diego.mediateca.domain;

import java.util.Objects;

public abstract class Material {
    protected final String idInterno;   // p.ej. LIB00001, REV00001
    protected final String titulo;
    protected int unidadesDisponibles;

    protected Material(String idInterno, String titulo, int unidadesDisponibles) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (unidadesDisponibles < 0) {
            throw new IllegalArgumentException("Las unidades disponibles no pueden ser negativas.");
        }
        this.idInterno = Objects.requireNonNull(idInterno, "idInterno es obligatorio");
        this.titulo = titulo.trim();
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getIdInterno() { return idInterno; }
    public String getTitulo() { return titulo; }
    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) {
        if (unidadesDisponibles < 0) throw new IllegalArgumentException("Unidades no pueden ser negativas.");
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public abstract String tipo(); // "Libro" o "Revista"

    @Override
    public String toString() {
        return "%s{id='%s', titulo='%s', unidades=%d}".formatted(
                tipo(), idInterno, titulo, unidadesDisponibles);
    }
}
