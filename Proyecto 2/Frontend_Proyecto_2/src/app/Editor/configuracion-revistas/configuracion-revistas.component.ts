import { ListaDeRevistasService } from './../../services/servicioAprobacionDeRevistas/lista-de-revistas.service';
import { Revista } from './../../models/revista.model';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { token } from '../../token';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-configuracion-revistas',
  standalone: true,
  imports: [FormsModule,RouterModule, CommonModule],
 templateUrl: './configuracion-revistas.component.html',
  styleUrl: './configuracion-revistas.component.css'
})
export class ConfiguracionRevistasComponent {
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

  cambiarEstadoLikes(idRevista: string, likes: boolean) {
    this.cargando = true;
    const formData = new FormData();
    formData.append('idRevista', idRevista);
    formData.append('likes', JSON.stringify(likes)); // Convertir el booleano a cadena
    this.ListaDeRevistasService.cambiarEstadoLikes(formData).subscribe(
      (response: any) => {
        setTimeout(() => {
          this.mensaje=response.message;
            this.cargando = false;
            this.mensajeExito=true;
            setTimeout(() => {
              this.mensajeExito=false;
              // Terminar la carga después de 500ms
            }, 2000);
            this.cargarDatos();
          // Terminar la carga después de 500ms
        }, 3000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        // Simulamos una pequeña pausa antes de quitar el estado de procesando en caso de error
        setTimeout(() => {
          this.Procesando = false; // Terminar la carga
        }, 1000);
      }
    );
  }

  cambiarEstadoComentarios(idRevista: string, comentarios: boolean) {
    this.cargando = true;
    const formData = new FormData();
    formData.append('idRevista', idRevista);
    formData.append('comentarios', JSON.stringify(comentarios)); // Convertir el booleano a cadena
    this.ListaDeRevistasService.cambiarEstadoComentarios(formData).subscribe(
      (response: any) => {
        setTimeout(() => {
          this.mensaje=response.message;
            this.cargando = false;
            this.mensajeExito=true;
            setTimeout(() => {
              this.mensajeExito=false;
              // Terminar la carga después de 500ms
            }, 2000);
            this.cargarDatos();
          // Terminar la carga después de 500ms
        }, 3000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        // Simulamos una pequeña pausa antes de quitar el estado de procesando en caso de error
        setTimeout(() => {
          this.Procesando = false; // Terminar la carga
        }, 1000);
      }
    );
  }

  cambiarEstadoSuscripciones(idRevista: string, suscripciones: boolean) {
    this.cargando = true;
    const formData = new FormData();
    formData.append('idRevista', idRevista);
    formData.append('suscripciones', JSON.stringify(suscripciones)); // Convertir el booleano a cadena
    this.ListaDeRevistasService.cambiarEstadoSuscripciones(formData).subscribe(
      (response: any) => {
        setTimeout(() => {
          this.mensaje=response.message;
            this.cargando = false;
            this.mensajeExito=true;
            setTimeout(() => {
              this.mensajeExito=false;
              // Terminar la carga después de 500ms
            }, 2000);
            this.cargarDatos();
          // Terminar la carga después de 500ms
        }, 3000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        // Simulamos una pequeña pausa antes de quitar el estado de procesando en caso de error
        setTimeout(() => {
          this.Procesando = false; // Terminar la carga
        }, 1000);
      }
    );
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
    this.ListaDeRevistasService.listaRevistasUsuario(formData).subscribe(
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

