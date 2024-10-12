import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class DuracionAnuncioService {


  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/duracionAnuncio';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  cambiarDuracion(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
