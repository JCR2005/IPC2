import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CompraAnuncioService } from '../../services/servicioCompraAnuncio/compra-anuncio.service';
import { token } from '../../token';
import { Anuncio } from '../../models/anuncio.model';
import { AnuncioService } from '../../services/servicioAnuncioEstado/anuncioEstadoGeneral.service';

@Component({
  selector: 'app-estado-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './estado-anuncios.component.html',
  styleUrls: ['./estado-anuncios.component.css']
})
export class EstadoAnunciosAdminComponent {
  anuncios: Anuncio[] = [];
  usuario: string = "";
  mensaje: string = "";
  cargando: boolean = false;
  Procesando: boolean = false; // Nueva propiedad para el estado de carga
  panelMensaje: boolean = false;
  procesoExitoso:boolean=false;
  window:boolean=true;

  constructor(
    private AnuncioService: AnuncioService
  ) {}

  cambiarEstadoAnuncio(anuncioId: string, estado: boolean) {
    this.Procesando = true; // Iniciar el estado de procesando
    this.window=false;

    const formData = new FormData();
    formData.append('idAnuncio', anuncioId);
    formData.append('estado', JSON.stringify(estado)); // Convertir el booleano a cadena

    this.AnuncioService.cambiarEstado(formData).subscribe(
      (response: any) => {
        // Simulamos una pequeña pausa antes de quitar el estado de procesando
        this.Procesando = false; // Terminar la carga
        if(response.cambioExitoso===true){
          this.procesoExitoso=true;
        }
        this.mensaje = response.mensaje;
        this.mostrarMensaje();
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
  cerrarPanelMensaje(){
    this.panelMensaje=false;
    this.window=true;
    this.cargarDatos();
  }
  mostrarMensaje() {
    this.panelMensaje = true;
  }

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargando = true; // Iniciar la carga

    this.AnuncioService.listaAnuncios().subscribe(
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

