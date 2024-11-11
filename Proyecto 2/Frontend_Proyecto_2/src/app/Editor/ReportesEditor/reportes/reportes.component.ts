import { ReporteDesuscripcionesComponent } from './../reporte-de-suscripciones/reporte-de-suscripciones.component';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReporteDeComentariosComponent } from '../reporte-de-comentarios/reporte-de-comentarios.component';
import { ReporteDepagosComponent } from '../reporte-de-pagos/reporte-de-pagos.component';
import { ReporteDeRevistasMasGustadasComponent } from '../reporte-de-revistas-mas-gustadas/reporte-de-revistas-mas-gustadas.component';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [ReporteDeRevistasMasGustadasComponent,ReporteDepagosComponent,ReporteDesuscripcionesComponent,CommonModule,RouterModule,ReporteDeComentariosComponent,ReporteDesuscripcionesComponent],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.css'
})
export class ReportesComponent {

  panelOpciones=false;
  panelReportesComentarios=false;
  panelReportesRevistasMasGustadas=false;
  panelReportesPagos=false;
  panelReportesSuscripciones=false;
  panelGrande: boolean = true;

  abrirPanel(){

    this.panelOpciones=!this.panelOpciones;
    this.panelGrande = false;
    this.panelGrande = !this.panelGrande;
  }


  abrirPanelReportesComentarios(){

    this.panelReportesComentarios=true
    this.panelReportesSuscripciones=false;
    this.panelReportesPagos=false;
    this.panelReportesRevistasMasGustadas=false;


    this.panelOpciones=false;
    this.panelGrande = true;
  }

  abrirPanelReportesSusucripciones(){
    this.panelReportesComentarios=false;
    this.panelReportesPagos=false;
    this.panelReportesSuscripciones=true
    this.panelReportesRevistasMasGustadas=false;

    this.panelOpciones=false;
    this.panelGrande = true;
  }

  abrirPanelReportesPagos(){
    this.panelReportesComentarios=false;
    this.panelReportesRevistasMasGustadas=false;
    this.panelReportesSuscripciones=false
    this.panelReportesPagos=true;
    this.panelOpciones=false;
    this.panelGrande = true;
  }



  abrirPanelReportesRevistasMasGustadas(){
    this.panelReportesComentarios=false;

    this.panelReportesSuscripciones=false
    this.panelReportesPagos=false;
    this.panelReportesRevistasMasGustadas=true;
    this.panelOpciones=false;
    this.panelGrande = true;
  }
}

