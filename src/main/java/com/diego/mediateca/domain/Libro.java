package com.diego.mediateca.domain;

import java.time.Year;

public class Libro extends MaterialEscrito {
    private final String autor;
    private final int numeroPaginas;
    private final String isbn;
    private final int anioPublicacion;

    public Libro(String idInterno, String titulo, String autor, int numeroPaginas,
                 String editorial, String isbn, int anioPublicacion, int unidadesDisponibles) {
        super(idInterno, titulo, unidadesDisponibles, editorial);
        if (autor == null || autor.isBlank()) throw new IllegalArgumentException("El autor es obligatorio.");
        if (numeroPaginas <= 0) throw new IllegalArgumentException("Número de páginas debe ser > 0.");
        if (isbn == null || isbn.isBlank()) throw new IllegalArgumentException("ISBN es obligatorio.");
        int current = Year.now().getValue();
        if (anioPublicacion < 1450 || anioPublicacion > current)
            throw new IllegalArgumentException("Año de publicación inválido.");

        this.autor = autor.trim();
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn.trim();
        this.anioPublicacion = anioPublicacion;
    }

    public String getAutor() { return autor; }
    public int getNumeroPaginas() { return numeroPaginas; }
    public String getIsbn() { return isbn; }
    public int getAnioPublicacion() { return anioPublicacion; }

    @Override public String tipo() { return "Libro"; }
}
