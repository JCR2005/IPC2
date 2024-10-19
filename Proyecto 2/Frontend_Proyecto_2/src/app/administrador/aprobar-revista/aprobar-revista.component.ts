import { Component } from '@angular/core';
import { ListaDeRevistasService } from '../../services/servicioAprobacionDeRevistas/lista-de-revistas.service';
import { Revista } from '../../models/revista.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-aprobar-revista',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './aprobar-revista.component.html',
  styleUrl: './aprobar-revista.component.css'
})
export class AprobarRevistaComponent {
  constructor(
  private ListaDeRevistasService: ListaDeRevistasService) {}
  costoOcultacion:number=0;
  costoAsociado:number=0;
  panelAprobacion: boolean=false;
  mensajeErrorcostoAsociado: boolean=false;
  mensajeErrorcostoOcultacion: boolean=false;
  cargando: boolean=false;
  mensajeExito: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;
  revistas: Revista[] = [];
  revistaEnRevision: string="";
  aprobacionExitosa=true;


  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargando = true; // Iniciar la carga

    this.ListaDeRevistasService.listarRevistas().subscribe(
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

  cerrarPanelAprobacion() {

    this.panelAprobacion=false;
  }

  cambiarEstadoAnuncio(RevistaId: string) {

    this.panelAprobacion=true;
    this.revistaEnRevision=RevistaId;
  }

  onSubmit() {
    this.aprobar();
}


aprobar(){
  this.cargando = true;
    const formData = new FormData();
    formData.append('idRevista', this.revistaEnRevision);
    formData.append('costoAsociado', this.costoAsociado.toString());
    formData.append('costoOcultacion', this.costoOcultacion.toString());


    this.ListaDeRevistasService.aprobarRevista(formData).subscribe(
      (response: any) => {


        this.panelAprobacion=false;
        if(response.costoAsociado==false){
          this.mensajeErrorcostoAsociado = true;
          this.aprobacionExitosa=false;
        }else{
          this.mensajeErrorcostoAsociado = false;
        }

        if(response.costoOcultacion==false){
          this.mensajeErrorcostoOcultacion = true;
          this.aprobacionExitosa=false;
        }else{
          this.mensajeErrorcostoOcultacion = false;
        }

        setTimeout(() => {

          if(this.aprobacionExitosa==false){
            this.panelAprobacion=true;
          }else{
            this.cargando = false;
            this.mensajeExito=true;
            setTimeout(() => {

              this.mensajeExito=false;
              this.panelAprobacion=false;

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
