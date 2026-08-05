package com.proyecto.bookBoxd.model;

public enum Genero {

	    FANTASIA("Fantasía"),
	    CIENCIA_FICCION("Ciencia ficción"),
	    ROMANCE("Romance"),
	    MISTERIO("Misterio"),
	    POLICIAL("Policial"),
	    THRILLER("Thriller"),
	    TERROR("Terror"),
	    DRAMA("Drama"),
	    AVENTURA("Aventura"),
	    HISTORICO("Histórico"),
	    FICCION_CONTEMPORANEA("Ficción contemporánea"),
	    REALISMO_MAGICO("Realismo mágico"),
	    DISTOPIA("Distopía"),
	    LITERATURA_JUVENIL("Literatura juvenil"),
	    LITERATURA_INFANTIL("Literatura infantil"),
	    COMEDIA("Comedia"),
	    POESIA("Poesía"),
	    BIOGRAFIA("Biografía"),
	    AUTOBIOGRAFIA("Autobiografía"),
	    ENSAYO("Ensayo"),
	    FILOSOFIA("Filosofía"),
	    PSICOLOGIA("Psicología"),
	    HISTORIA("Historia"),
	    CIENCIA("Ciencia"),
	    AUTOAYUDA("Autoayuda"),
	    OTROS("Otros");

    private final String nombreMostrar;

    Genero(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }
}