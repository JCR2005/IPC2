import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class token {


  public parseJwt(token: string): any {
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
