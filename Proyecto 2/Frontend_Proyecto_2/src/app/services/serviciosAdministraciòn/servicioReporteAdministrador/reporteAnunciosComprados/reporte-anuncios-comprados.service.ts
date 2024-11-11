import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';




@Injectable({
  providedIn: 'root'
})
export class ReporteAnunciosCompradosService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ReportePagosAdministracios/pagosCompraAnuncios';

  constructor(private http: HttpClient) {}

  obtenerReporteGeneral(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }


}
