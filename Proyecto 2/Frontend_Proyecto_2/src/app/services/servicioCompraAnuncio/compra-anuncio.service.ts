import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CompraAnuncioService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/compraAnuncio';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/compraAnuncio/listaAnuncios';
  private apiUrl3 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/compraAnuncio/cambioEstado';

  constructor(private http: HttpClient) {}

  realizarCompra(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  consultarAnuncios(usuarioData: any): Observable<any> {
    return this.http.post(this.apiUrl2, usuarioData);  // Enviar el JSON directamente
  }

  cambiarEstado(idAnuncio: any): Observable<any> {
    return this.http.post(this.apiUrl3, idAnuncio);  // Enviar el JSON directamente
  }
}
