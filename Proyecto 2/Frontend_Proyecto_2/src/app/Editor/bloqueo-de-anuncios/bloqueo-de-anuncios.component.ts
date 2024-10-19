import { ListaDeRevistasService } from './../../services/servicioAprobacionDeRevistas/lista-de-revistas.service';
import { Revista } from './../../models/revista.model';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { token } from '../../token';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-bloqueo-de-anuncios',
  standalone: true,
  imports: [FormsModule,RouterModule, CommonModule],
  templateUrl: './bloqueo-de-anuncios.component.html',
  styleUrl: './bloqueo-de-anuncios.component.css'
})
export class BloqueoDeAnunciosComponent {
  revistas: Revista[] = [];
  usuario: string = "";
  mensaje: string = "";
  cargando: boolean = false;
  Procesando: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;
  mensajeExito: boolean = false;


  constructor(
    private ListaDeRevistasService: ListaDeRevistasService,
    private token: token
  ) {}


  bloquearAdda(idRevista: string) {


  }
  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargando = true; // Iniciar la carga
    const token = sessionStorage.getItem('token');
    if (token) {
      try {
        const payload = this.token.parseJwt(token);
        this.usuario = payload.usuario;
      } catch (error) {
        console.error('Error al parsear el token:', error);
      }
    }
    const formData = new FormData();
    formData.append('usuario',this.usuario);
    this.ListaDeRevistasService.listaRevistasConAdds(formData).subscribe(
      (response: Revista[]) => {
        this.revistas = response;
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      }
    );
  }
}
