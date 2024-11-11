import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';



@Injectable({
  providedIn: 'root'
})
export class ReporteGananciasAnunciantesService {


  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ReporteGananciasPorAnunciantess/listaUsuarios';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ReporteGananciasPorAnunciantess/ingresosAnunciantes';


  constructor(private http: HttpClient) {}

  obtenerUsuarios(): Observable<any> {
    return this.http.get(this.apiUrl);
  }


  obtenerReporteGeneral(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1, formData);
  }

}

