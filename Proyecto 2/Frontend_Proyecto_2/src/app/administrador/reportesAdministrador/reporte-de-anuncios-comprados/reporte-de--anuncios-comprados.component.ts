import { ReporteAnunciosCompradosService } from '../../../services/serviciosAdministraciòn/servicioReporteAdministrador/reporteAnunciosComprados/reporte-anuncios-comprados.service';
import { pago } from '../../../models/pago.model';
import { Component } from '@angular/core';
import { token } from '../../../token';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-reporte-de-ReporteDeAnunciosCompradosComponent',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reporte-de--anuncios-comprados.component.html',
  styleUrl: './reporte-de-anuncios-comprados.component.css'
})
export class ReporteDeAnunciosCompradosComponent {
  pdfVisualizacion: SafeResourceUrl | undefined;
  nombreReporte="";
  panelFiltro=false;
  pagos: pago[] = [];
  tipoAnuncios: string[] = [];

  panelReporte=false;
  fechaInicio: string = '';  // Campo para almacenar la fecha de inicio
  fechaFin: string = '';     // Campo para almacenar la fecha de fin
  tipoAnucioSelecionado: string = '';  // Campo para almacenar la revista seleccionada


  constructor( private sanitizer: DomSanitizer, private cdr: ChangeDetectorRef ,private token:token,private ReporteAnunciosCompradosService:ReporteAnunciosCompradosService
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
    formData.append('tipoAnuncio',this.tipoAnucioSelecionado);
    this.nombreReporte="reporte desde "+this.fechaInicio+" hasta"+this.fechaFin+"/Anuncios tipo "+this.tipoAnucioSelecionado+".pdf"

    this.ReporteAnunciosCompradosService.obtenerReporteGeneral(formData).subscribe(
      (response: any) => {
        this.pagos=response.ingresos;
        this.tipoAnuncios=response.tiposAnuncio;
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
