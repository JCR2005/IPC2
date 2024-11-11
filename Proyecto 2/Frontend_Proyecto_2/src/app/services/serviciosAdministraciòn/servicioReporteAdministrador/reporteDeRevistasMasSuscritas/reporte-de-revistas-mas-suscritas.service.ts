import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';




@Injectable({
  providedIn: 'root'
})
export class ReporteDeRevistasMasSuscritasService {

  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/RestReporteRvistasTop5Suscripciones/Reporte';


  constructor(private http: HttpClient) {}

  obtenerReporteGeneral(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1, formData);
  }
}
