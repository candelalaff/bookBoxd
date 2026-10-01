import { HttpInterceptorFn } from '@angular/common/http';

// Interceptor funcional: intercepta TODAS las peticiones HTTP salientes.
// Le agrega el header Authorization con el token JWT guardado, excepto
// en las rutas de autenticacion (/api/auth/**), donde el backend no lo necesita
// (y un token viejo pinvalido causa problemas).
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // No agrego el header en login/registro
  if (req.url.includes('/api/auth/')) {
    return next(req);
  }

  const token = localStorage.getItem('token');

  if (token) {
    const reqClonado = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next(reqClonado);
  }

  // no hay token -> mando la peticion tal cual (el backend la va a rechazar
  // con 401/403 si el endpoint requiere autenticacion)
  return next(req);
};