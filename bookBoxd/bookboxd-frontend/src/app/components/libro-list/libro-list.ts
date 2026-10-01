import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth';
import { LibroService } from '../../services/libro';

@Component({
  selector: 'app-libro-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './libro-list.html',
  styleUrl: './libro-list.css'
})
export class LibroListComponent implements OnInit {

  librosGoogle: any[] = [];
  terminoBusqueda: string = 'fantasy';
  cargando: boolean = false;
  mensajeError: string | null = null;

  constructor(
    private authService: AuthService,
    private libroService: LibroService,
    private router: Router,
    // Inyecto ChangeDetectorRef porque la pantalla se me quedaba "dormida"
    // y no mostraba las portadas hasta que yo hacía un clic extra en cualquier lado.
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.cargarLibros();
  }

  cargarLibros(): void {
    this.cargando = true;
    this.mensajeError = null;

    this.libroService.buscarLibrosGoogle(this.terminoBusqueda).subscribe({
      next: (data: any) => {
        // El backend puede responder con un JSON de error (cuota agotada, etc.)
        // en vez de la respuesta normal de Google Books
        if (data && data.error) {
          this.mensajeError = data.error;
          this.librosGoogle = [];
        } else {
          this.librosGoogle = data.items || [];
        }
        this.cargando = false;
        
        // Le aviso a la vista de Angular que los datos ya llegaron y que fuerce
        // la actualización del HTML YA MISMO, sin esperar a que el usuario haga clic.
        this.cdr.detectChanges(); 
      },
      error: (err: any) => {
        console.error('Error al obtener libros de Google:', err);
        this.mensajeError = 'No se pudo cargar el catalogo en este momento.';
        this.cargando = false;
        
        // Lo pongo acá también por las dudas, para que si hay un error
        // el cartelito rojo aparezca instantáneamente.
        this.cdr.detectChanges(); 
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}