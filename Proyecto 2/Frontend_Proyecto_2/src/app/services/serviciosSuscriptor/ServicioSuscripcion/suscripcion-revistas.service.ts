import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class SuscripcionRevistasService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Suscripciones/listaRevistas';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Suscripciones/suscripcion';


  constructor(private http: HttpClient) {}

  listarRevistas(formData: any): Observable<any> {
    return this.http.post(this.apiUrl,formData);
  }

  suscribirRevista(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1, formData);
  }

}
