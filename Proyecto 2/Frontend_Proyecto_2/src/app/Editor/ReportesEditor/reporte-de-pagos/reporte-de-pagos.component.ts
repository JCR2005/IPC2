import { ReportePagosService } from './../../../services/serviciosEditor/serviciosReportesEditor/reportePagos/reporte-pagos.service';
import { pago } from './../../../models/pago.model';
import { Component } from '@angular/core';
import { token } from '../../../token';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { Revista } from '../../../models/revista.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-reporte-de-pagos',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reporte-de-pagos.component.html',
  styleUrl: './reporte-de-pagos.component.css'
})
export class ReporteDepagosComponent {
  pdfVisualizacion: SafeResourceUrl | undefined;
  nombreReporte="";
  panelFiltro=false;
  pagos: pago[] = [];
  Revistas: Revista[] = [];
  panelReporte=false;
  fechaInicio: string = '';  // Campo para almacenar la fecha de inicio
  fechaFin: string = '';     // Campo para almacenar la fecha de fin
  revistaSeleccionada: string = '';  // Campo para almacenar la revista seleccionada
  totalPago=0;

  constructor( private sanitizer: DomSanitizer,private cdr: ChangeDetectorRef ,private token:token,private ReportePagosService:ReportePagosService
  ) {}

  ngOnInit() {
    const formData = new FormData();
    formData.append('idUsuario', this.token.obtenerUsuario());
    this.ReportePagosService.obtenerRevistas(formData).subscribe(
      (response: any) => {
        this.panelFiltro=true;

        this.Revistas=response.revistas;

      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );

  }

abrirPanelFiltro(){

  this.panelFiltro=!this.panelFiltro;
  this.panelReporte=!this.panelReporte;
}

  obtenerReporteGenerar(){

    const formData = new FormData();
    formData.append('idUsuario', this.token.obtenerUsuario());
    formData.append('fechaInicio', this.fechaInicio);
    formData.append('fechaFin', this.fechaFin);
    formData.append('idRevista',this.revistaSeleccionada);
    this.nombreReporte="reporte desde "+this.fechaInicio+" hasta"+this.fechaFin+" "+this.revistaSeleccionada+".pdf"

    this.ReportePagosService.obtenerReporteGeneral(formData).subscribe(
      (response: any) => {
        this.pagos=response.ingresos;

        this.totalPago=response.totalPago;
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
