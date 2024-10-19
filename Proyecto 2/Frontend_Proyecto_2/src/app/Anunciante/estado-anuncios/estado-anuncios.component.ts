import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CompraAnuncioService } from '../../services/servicioCompraAnuncio/compra-anuncio.service';
import { token } from '../../token';
import { Anuncio } from '../../models/anuncio.model';

@Component({
  selector: 'app-estado-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './estado-anuncios.component.html',
  styleUrls: ['./estado-anuncios.component.css']
})
export class EstadoAnunciosComponent {
  anuncios: Anuncio[] = [];
  usuario: string = "";
  mensaje: string = "";
  cargando: boolean = false;
  Procesando: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;

  constructor(
    private compraAnuncioService: CompraAnuncioService,
    private token: token
  ) {}

  cambiarEstadoAnuncio(anuncioId: string, estado: boolean) {
    this.Procesando = true; // Iniciar el estado de procesando

    const formData = new FormData();
    formData.append('idAnuncio', anuncioId);
    formData.append('estado', JSON.stringify(estado)); // Convertir el booleano a cadena

    this.compraAnuncioService.cambiarEstado(formData).subscribe(
      (response: any) => {
        // Simulamos una pequeña pausa antes de quitar el estado de procesando
        setTimeout(() => {
          this.Procesando = false; // Terminar la carga
        }, 1000);

        this.mensaje = response.message;

        this.cargarDatos();

      },
      (error) => {
        console.error('Error al enviar los datos:', error);

        // Simulamos una pequeña pausa antes de quitar el estado de procesando en caso de error
        setTimeout(() => {
          this.Procesando = false; // Terminar la carga
        }, 1000);
      }
    );

    this.mostrarMensaje();
  }

  mostrarMensaje() {

    this.respuesta = true;

    // Esperar 2 segundos (2000 milisegundos) antes de establecer respuesta a false
    setTimeout(() => {
      this.respuesta = false;
    }, 20000);
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

    this.compraAnuncioService.consultarAnuncios(formData).subscribe(
      (response: Anuncio[]) => {
        this.anuncios = response;

        // Añadimos un pequeño retraso antes de ocultar el panel de carga
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);

        // Añadimos un pequeño retraso antes de ocultar el panel de carga en caso de error
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      }
    );
  }
}

