
import { Component } from '@angular/core';
import { ListaDeRevistasService } from '../../services/servicioAprobacionDeRevistas/lista-de-revistas.service';
import { Revista } from '../../models/revista.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-modificar-costo-ocultacion',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './modificar-costo-ocultacion.component.html',
  styleUrl: './modificar-costo-ocultacion.component.css'
})
export class ModificarCostoOcultacionComponent {
  constructor(
  private ListaDeRevistasService: ListaDeRevistasService) {}
  costoOcultacion:number=0;
  costoAsociado:number=0;
  panelDeActualizacion: boolean=false;
  mensajeErrorcostoAsociado: boolean=false;
  mensajeErrorcostoOcultacion: boolean=false;
  cargando: boolean=false;
  mensajeExito: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;
  revistas: Revista[] = [];
  revistaEnRevision: string="";
  aprobacionExitosa=true;
  costoOcultacionActual:number=0;
  mensaje:string="";

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargando = true; // Iniciar la carga

    this.ListaDeRevistasService.listarRevistasAporbadas().subscribe(
      (response: Revista[]) => {
        this.revistas = response;

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

  cerrarPanelActualizacion() {

    this.panelDeActualizacion=false;
  }

  cambiarCosto(RevistaId: string, costoActual:number) {

    this.panelDeActualizacion=true;
    this.revistaEnRevision=RevistaId;
    this.costoOcultacion=costoActual;
    this.costoOcultacionActual=costoActual;
  }

  onSubmit() {
    this.actualizar();

}


actualizar(){
    this.cargando = true;
    this.panelDeActualizacion=false;
    const formData = new FormData();
    formData.append('idRevista', this.revistaEnRevision);
    formData.append('costoOcultacion', this.costoOcultacion.toString());


    this.ListaDeRevistasService.actualizacionCostoOcultacion(formData).subscribe(
      (response: any) => {
        this.mensaje=response.mensaje;

        if(response.costoOcultacion==false){
          this.mensajeErrorcostoOcultacion = true;
          this.aprobacionExitosa=false;
        }else{
          this.mensajeErrorcostoOcultacion = false;
        }

        setTimeout(() => {

          if(this.aprobacionExitosa==false){
            this.panelDeActualizacion=true;
          }else{
            this.cargando = false;
            this.mensajeExito=true;
            setTimeout(() => {

              this.mensajeExito=false;
              this.panelDeActualizacion=false;

              // Terminar la carga después de 500ms
            }, 2000);
            this.cargarDatos();
          }

          // Terminar la carga después de 500ms
        }, 3000);


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
