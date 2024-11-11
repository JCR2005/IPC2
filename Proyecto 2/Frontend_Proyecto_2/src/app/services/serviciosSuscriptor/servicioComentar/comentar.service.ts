import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ComentarService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Comentarios/crearComentario';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Comentarios/comentarios';

  constructor(private http: HttpClient) {}

  comentar(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  comentariosRevista(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1, formData);
  }
}
