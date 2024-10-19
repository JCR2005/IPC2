import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CrearRevistaService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/creacion';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  crearRevista(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
