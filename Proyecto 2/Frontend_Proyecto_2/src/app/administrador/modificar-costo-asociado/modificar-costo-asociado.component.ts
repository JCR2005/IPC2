import { RevistasService } from './../../services/serviciosAdministraciòn/Revistas/revistas.service';

import { Component } from '@angular/core';
import { Revista } from '../../models/revista.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-modificar-costo-ocultacion',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './modificar-costo-asociado.component.html',
  styleUrl: './modificar-costo-asociado.component.css'
})
export class ModificarCostoAsociadoComponent {
  constructor(
  private RevistasService: RevistasService) {}

  costoAsociado:number=0;
  panelDeActualizacion: boolean=false;
  mensajeErrorcostoAsociado: boolean=false;

  cargando: boolean=false;
  mensajeExito: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;
  revistas: Revista[] = [];
  revistaEnRevision: string="";
  aprobacionExitosa=true;
  costoAsociadoActual:number=0;
  mensaje:string="";
  costoGlobalAsociado: number= 0.00;
  ngOnInit(): void {
    this.cargarDatos();
    this.obtenerDatos();
  }

  obtenerDatos(){

    this.RevistasService.obtenerCostoAsociadoGlobal().subscribe(
      (response: any) => {

        this.costoGlobalAsociado=response.costosGlobales.costo;
        this.costoAsociado=this.costoGlobalAsociado;

      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }
  cargarDatos() {
    this.cargando = true; // Iniciar la carga

    this.RevistasService.listarRevistasAporbadas().subscribe(
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
    this.costoAsociado=costoActual;
    this.costoAsociadoActual=costoActual;
  }

  onSubmit() {
    this.actualizar();

}


actualizar(){
    this.cargando = true;
    this.panelDeActualizacion=false;
    const formData = new FormData();
    formData.append('idRevista', this.revistaEnRevision);
    formData.append('costoAsociado', this.costoAsociado.toString());


    this.RevistasService.actualizacionCostoAsociado(formData).subscribe(
      (response: any) => {
        this.mensaje=response.mensaje;

        if(response.ActualizacionCostoAsociadoExitoso==false){
          this.mensajeErrorcostoAsociado = true;
          this.aprobacionExitosa=false;
        }else{
          this.mensajeErrorcostoAsociado = false;
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
