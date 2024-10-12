import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompraAnuncioService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/compraAnuncio';

  constructor(private http: HttpClient) {} // Asegúrate de que HttpClient se inyecta correctamente

  realizarCompra(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
