import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListaDeRevistasService {
  private apiUrl = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/aprobacion';
  private apiUrl1 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/aprobacion';
  private apiUrl2 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/listasAprobadas';
  private apiUrl3 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/actualizacionCostoOcultacion';
  private apiUrl4 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/listaRevistasUsuario';
  private apiUrl5 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/cambioEstadoLikes';
  private apiUrl6 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/cambioEstadoComentarios';
  private apiUrl7 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/cambioEstadoSuscripciones';
  private apiUrl8 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/listaRevistasAdda';
  private apiUrl9 = 'http://localhost:8080/BACKEND_PROYECTO_2/resources/revista/bloquearAnuncios';

  constructor(private http: HttpClient) {}

  listarRevistas  (): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  aprobarRevista(formData: any): Observable<any> {
    return this.http.post(this.apiUrl1,formData);
  }

  listarRevistasAporbadas(): Observable<any> {
    return this.http.get(this.apiUrl2);
  }

  actualizacionCostoOcultacion(formData: any): Observable<any> {
    return this.http.post(this.apiUrl3,formData);
  }

  listaRevistasUsuario(formData: any): Observable<any> {
    return this.http.post(this.apiUrl4,formData);
  }

  cambiarEstadoLikes(formData: any): Observable<any> {
    return this.http.post(this.apiUrl5,formData);
  }

  cambiarEstadoComentarios(formData: any): Observable<any> {
    return this.http.post(this.apiUrl6,formData);
  }

  cambiarEstadoSuscripciones(formData: any): Observable<any> {
    return this.http.post(this.apiUrl7,formData);
  }

  listaRevistasConAdds(formData: any): Observable<any> {
    return this.http.post(this.apiUrl8,formData);
  }

  bloquearAdds(formData: any): Observable<any> {
    return this.http.post(this.apiUrl9,formData);
  }

}
