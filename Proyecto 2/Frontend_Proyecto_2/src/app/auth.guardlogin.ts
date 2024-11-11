import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class loginGuardia implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = sessionStorage.getItem('token');

    if (token) {
      try {
        const payload = this.parseJwt(token);

        // Verificar el tipo de cuenta y redirigir según corresponda
        if (payload && payload.tipoCuenta === 'Editor') {
          this.router.navigate(['/paginaPrincipalEditor']);
          return false; // Bloquear acceso a la página de login
        } else if(payload && payload.tipoCuenta === 'Anunciante') {
          this.router.navigate(['/paginaprincipalanunciante']);
          return false; // Bloquear acceso a la página de login
        }else if(payload && payload.tipoCuenta === 'Administrador') {
          this.router.navigate(['/paginaPrincipalAdministrador']);
          return false; // Bloquear acceso a la página de login
        }else if(payload && payload.tipoCuenta === 'Suscriptor') {
          this.router.navigate(['/paginaPrincipalSuscriptor']);
          return false; // Bloquear acceso a la página de login
        }else {
          this.router.navigate(['/registro']);
          return false; // Bloquear acceso a la página de login
        }
      } catch (error) {
        console.error('Error al decodificar el token:', error);
        
        return true; // Permitir acceso a la página de login si el token es inválido
      }
    }

    // Si no hay token, permitir acceso a la página de login
    return true;
  }

  // Método para decodificar el token JWT
  private parseJwt(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      if (!base64Url) {
        throw new Error('Token mal formado');
      }
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64));
    } catch (error) {
      console.error('Error al decodificar el token JWT:', error);
      throw error;
    }
  }
}
