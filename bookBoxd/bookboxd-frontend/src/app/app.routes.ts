import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { LibroListComponent } from './components/libro-list/libro-list';

export const routes: Routes = [
  // Ruta por defecto: redirige al login
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'libros', component: LibroListComponent }
];