import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VisualizacionRevistaService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Visualizaciòn/listaRevistasSuscritas';

  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Visualizaciòn/articulo';

  constructor(private http: HttpClient) {}

  listarRevistas(formData: any): Observable<any> {
    return this.http.post(this.apiUrl,formData);
  }

  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/Visualizaciòn/listaAticulos';



  listarArticulos(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1,formData);
  }

  traerArtiiculo(formData: any): Observable<any> {
    return this.http.post(this.apiUrl2,formData);
  }
}
