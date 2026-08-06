import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { Libro } from '../../models/libro';

@Component({
  selector: 'app-libro-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './libro-list.html',
  styleUrl: './libro-list.css'
})
export class LibroListComponent implements OnInit {

  // Lista mock para maquetar la interfaz antes de consumir la API
  libros: Libro[] = [
    { id: 1, titulo: 'Alas de Sangre', autor: 'Rebecca Yarros', genero: 'Fantasía', rating: 4.8 },
    { id: 2, titulo: 'Alas de Hierro', autor: 'Rebecca Yarros', genero: 'Fantasía', rating: 4.7 },
    { id: 3, titulo: 'Alas de Ónix', autor: 'Rebecca Yarros', genero: 'Fantasía', rating: 4.9 }
  ];

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}