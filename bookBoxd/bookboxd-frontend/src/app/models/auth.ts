// Define los datos que necesita el backend para loguear a un usuario
export interface LoginRequest {
  alias: string;
  password: string;
}

// Define los datos que exige el backend para registrar a un usuario nuevo
export interface RegisterRequest {
  alias: string;
  nombre: string;
  apellido: string;
  email: string;
  password: string;
}

// Define la respuesta que nos devuelve Spring Boot cuando el login es correcto (el token JWT)
export interface AuthResponse {
  accessToken: string;
  tokenType: string;
}