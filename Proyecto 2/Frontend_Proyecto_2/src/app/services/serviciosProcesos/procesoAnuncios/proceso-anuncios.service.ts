
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ProcesoAnunciosService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ProcesoAnuncios/anunciosTexto';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ProcesoAnuncios/AnuncioVideo';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ProcesoAnuncios/AnuncioImagen';
  private apiUrl3 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/ProcesoAnuncios/historial';



  constructor(private http: HttpClient) {}

  obtenerAnunciosTexto(): Observable<any> {

    return this.http.get(this.apiUrl);
  }
  obtenerAnuncioImagen(): Observable<any> {

    return this.http.get(this.apiUrl2);
  }
  obtenerAnunciosVideo(): Observable<any> {

    return this.http.get(this.apiUrl1);
  }

  enviarReporte(formdata:any): Observable<any> {

    return this.http.post(this.apiUrl3, formdata);
  }
}
