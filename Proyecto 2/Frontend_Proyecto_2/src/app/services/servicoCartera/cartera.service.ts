import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root',
})
export class carteraService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/cartera';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/cartera/obtenerSaldo';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  RecargarCartera(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  obtenerCartera(formData: any): Observable<any> {
    return this.http.post(this.apiUrl2, formData);
  }
}
