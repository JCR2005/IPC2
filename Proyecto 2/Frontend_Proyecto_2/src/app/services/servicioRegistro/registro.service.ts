import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RegistroService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Registro';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  registrarUsuario(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
