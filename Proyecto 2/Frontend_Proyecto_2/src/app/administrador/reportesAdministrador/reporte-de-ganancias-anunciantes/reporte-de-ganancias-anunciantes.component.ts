import { Usuario } from './../../../models/usuario.model';
import { ReporteGananciasAnunciantesService } from './../../../services/serviciosAdministraciòn/servicioReporteAdministrador/reporteGananciasAnunciantes/reporte-ganancias-anunciantes.service';
import { Component } from '@angular/core';
import { token } from '../../../token';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { pago } from '../../../models/pago.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-reporte-de-ReporteDeGananciasAnunciantesComponent',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './reporte-de-ganancias-anunciantes.component.html',
  styleUrl: './reporte-de-ganancias-anunciantes.component.css'
})
export class ReporteDeGananciasAnunciantesComponent {

  panelFiltro=false;
  Usuarios: Usuario[] = [];
  IngresosTotales: number[] = [];
  tipoAnuncios: string[][] = [];
  pagos: pago[][] = [];
  panelReporte=false;
  fechaInicio: string = '2000-01-01';  // Campo para almacenar la fecha de inicio
  fechaFin: string =  '2100-01-01';     // Campo para almacenar la fecha de fin
  AnuncianteSelecionado: string = '';  // Campo para almacenar la revista seleccionada
  pdfVisualizacion: SafeResourceUrl | undefined;
  nombreReporte="";

  constructor(private sanitizer: DomSanitizer, private cdr: ChangeDetectorRef ,private token:token,private ReporteGananciasAnunciantesService:ReporteGananciasAnunciantesService
  ) {}
cargarDatos(){
  this.ReporteGananciasAnunciantesService.obtenerUsuarios().subscribe(
    (response: any) => {
      this.panelFiltro=true;
      this.Usuarios=response.anunciantes;

    },
    (error) => {
      console.error('Error al enviar los datos:', error);
    }
  );

}
  ngOnInit() {

    this.cargarDatos();
  }

abrirPanelFiltro(){
   this.cargarDatos();
  this.panelFiltro=!this.panelFiltro;
  this.panelReporte=!this.panelReporte;
}

  obtenerReporteGenerar(){

    const formData = new FormData();
    formData.append('fechaInicio', this.fechaInicio);
    formData.append('fechaFin', this.fechaFin);
    formData.append('idUsuario',this.AnuncianteSelecionado);
    this.nombreReporte="reporte desde "+this.fechaInicio+" hasta"+this.fechaFin+"/Anuncios tipo "+this.AnuncianteSelecionado+".pdf"

    this.ReporteGananciasAnunciantesService.obtenerReporteGeneral(formData).subscribe(
      (response: any) => {

        this.Usuarios=response.anunciantes;
        this.tipoAnuncios=response.tiposDeAnuncio;
        this.pagos=response.ingresos;
        this.IngresosTotales=response.ganaciasPorAnunciante;
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
