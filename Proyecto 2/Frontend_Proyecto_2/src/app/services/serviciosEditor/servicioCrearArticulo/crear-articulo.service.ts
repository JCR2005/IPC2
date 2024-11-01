import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CrearArticuloService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/articulo/crearArticulo';

  constructor(private http: HttpClient) {}

  crearArticulo(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }
}
