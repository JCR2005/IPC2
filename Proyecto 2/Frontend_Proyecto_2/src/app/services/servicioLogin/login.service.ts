import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Login';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  iniciarSesion(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
