export interface Libro {
  id?: number;
  titulo: string;
  autor: string;
  genero: string;
  sinopsis?: string;
  imagenUrl?: string;
  rating?: number;
}