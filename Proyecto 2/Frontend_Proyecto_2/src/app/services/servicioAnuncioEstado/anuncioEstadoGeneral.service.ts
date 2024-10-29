import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AnuncioService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/AnunciosAdministracion/listaAnuncios';
  private apiUrl2= 'http://localhost:8080/BACKEND_PROYECTO_2/resources/AnunciosAdministracion/cambioEstado';
  constructor(private http: HttpClient) {}

  listaAnuncios(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  cambiarEstado(formData: any): Observable<any> {
    return this.http.post(this.apiUrl2, formData);  // Enviar el JSON directamente
  }
}
