import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RevistasService {
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revistas/actualizacionCostoAsociado';
  private apiUrl3 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revistas/costoAsociadoGlobal';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/listasAprobadas';
  private apiUrl4 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revistas/actualizacionCostoAsociadoGlobal';


  constructor(private http: HttpClient) {}

  actualizacionCostoAsociado(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1,formData);
  }

  actualizacionCostoAsociadoGlobal(formData: any): Observable<any> {
    return this.http.post(this.apiUrl4,formData);
  }
  listarRevistasAporbadas(): Observable<any> {
    return this.http.get(this.apiUrl2);
  }

  obtenerCostoAsociadoGlobal(): Observable<any> {
    return this.http.get(this.apiUrl3);
  }
}
