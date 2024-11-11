import { Usuario } from '../../../models/usuario.model';
import { Component } from '@angular/core';
import { ReporteDeRevistasMasComentadasService } from '../../../services/serviciosAdministraciòn/servicioReporteAdministrador/reporteDeRevistasMasComentadas/reporte-de-revistas-mas-comentadas.service';
import { token } from '../../../token';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { pago } from '../../../models/pago.model';
import { Comentario } from '../../../models/comentario.model';
import { Revista } from '../../../models/revista.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-reporte-de-ReporteDeRevistasMasComentadassComponent',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reporte-de-revistas-mas-comentadas.component.html',
  styleUrl: './reporte-de-revistas-mas-comentadas.component.css'
})
export class ReporteDeRevistasMasComentadassComponent {
  nombreReporte="";
  panelFiltro=false;
  revistas: Revista[] = [];
  IngresosTotales: number[] = [];
  tipoAnuncios: string[][] = [];
  Comentarios: Comentario[][] = [];
  panelReporte=false;
  pdfVisualizacion: SafeResourceUrl | undefined;
  fechaInicio: string = '2000-01-01';  // Campo para almacenar la fecha de inicio
  fechaFin: string =  '2100-01-01';     // Campo para almacenar la fecha de fin
  AnuncianteSelecionado: string = '';  // Campo para almacenar la revista seleccionada


  constructor( private sanitizer: DomSanitizer,private cdr: ChangeDetectorRef ,private token:token,private ReporteDeRevistasMasComentadasService:ReporteDeRevistasMasComentadasService
  ) {}

  ngOnInit() {

  }

abrirPanelFiltro(){
  this.panelFiltro=!this.panelFiltro;
  this.panelReporte=!this.panelReporte;
}

  obtenerReporteGenerar(){

    const formData = new FormData();
    formData.append('fechaInicio', this.fechaInicio);
    formData.append('fechaFin', this.fechaFin);

    this.nombreReporte="reporte desde "+this.fechaInicio+" hasta"+this.fechaFin+""+this.AnuncianteSelecionado+".pdf"

    this.ReporteDeRevistasMasComentadasService.obtenerReporteGeneral(formData).subscribe(
      (response: any) => {

        this.revistas=response.revistas;
        this.Comentarios=response.comentarios;
        this.panelFiltro=false;
        this.panelReporte=true;
        if (response.pdf) {

          this.pdfVisualizacion = this.sanitizer.bypassSecurityTrustResourceUrl(
            `data:application/pdf;base64,${response.pdf}`
          );
        }
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }


  obtenerFechaLegible(timestamp: string): string {
    const fecha = new Date(timestamp);
    return fecha.toLocaleDateString();
  }
}
