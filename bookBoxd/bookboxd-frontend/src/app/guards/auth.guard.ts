import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';

// Guard funcional: se ejecuta antes de entrar a cualquier ruta que lo tenga asignado.
// Usa AuthService.isLoggedIn() en vez de leer localStorage directamente, para que
// la logica de "como se guarda el token" viva en un unico lugar (AuthService)
// y nunca se desincronice entre el guard, el interceptor y el service.
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};