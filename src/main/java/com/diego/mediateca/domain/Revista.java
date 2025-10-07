package com.diego.mediateca.domain;

import java.time.LocalDate;

public class Revista extends MaterialEscrito {
    private final String periodicidad;      // semanal, mensual, ...
    private final LocalDate fechaPublicacion;

    public Revista(String idInterno, String titulo, String editorial, String periodicidad,
                   LocalDate fechaPublicacion, int unidadesDisponibles) {
        super(idInterno, titulo, unidadesDisponibles, editorial);
        if (periodicidad == null || periodicidad.isBlank())
            throw new IllegalArgumentException("La periodicidad es obligatoria.");
        if (fechaPublicacion == null)
            throw new IllegalArgumentException("La fecha de publicación es obligatoria.");
        if (fechaPublicacion.isAfter(LocalDate.now().plusDays(1)))
            throw new IllegalArgumentException("La fecha de publicación no puede ser futura.");

        this.periodicidad = periodicidad.trim();
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPeriodicidad() { return periodicidad; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }

    @Override public String tipo() { return "Revista"; }
}
