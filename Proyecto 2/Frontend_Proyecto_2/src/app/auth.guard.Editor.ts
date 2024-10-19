// admin.guard.ts

import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class editorGuardia implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = sessionStorage.getItem('token');

    if (token) {
      try {
        const payload = this.parseJwt(token);
        // Verifica si el tipo de cuenta es 'Administrador'
        if (payload && payload.tipoCuenta === 'Editor') {
          return true;
        }
      } catch (error) {
        console.error('Error al decodificar el token:', error);
        // Redirigir al login si el token no es válido o está mal formado
        this.router.navigate(['/login']);
        alert('Inicia sesion');
        return false;
      }
    }

    // Si no hay token o no es admin, redirigir a la página de inicio o login
    this.router.navigate(['/login']);
    alert('No tienes permiso para acceder a esta página. Inicia sesión como editor.');
    return false;
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
