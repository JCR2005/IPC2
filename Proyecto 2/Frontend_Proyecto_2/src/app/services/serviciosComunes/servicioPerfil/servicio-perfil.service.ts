
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServicioPerfilService {

  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/miPerfil/miPerfil';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/miPerfil/editarMiPerfil';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/miPerfil/verificarDatos';

  constructor(private http: HttpClient) {}

  obtenerPerfil(Usuario: any): Observable<any> {

    return this.http.post(this.apiUrl,Usuario);
  }

  editarPerfil(Usuario: any): Observable<any> {

    return this.http.post(this.apiUrl1,Usuario);
  }

  VerificarDatos(Usuario: any): Observable<any> {

    return this.http.post(this.apiUrl2,Usuario);
  }
}
