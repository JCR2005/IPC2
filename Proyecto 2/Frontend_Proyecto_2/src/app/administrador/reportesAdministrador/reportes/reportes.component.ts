import { ReporteDeAnunciosCompradosComponent } from './../reporte-de-anuncios-comprados/reporte-de--anuncios-comprados.component';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReporteDeRevistasMasSuscritassComponent } from '../reporte-de-revistas-mas-suscritas/reporte-de-revistas-mas-suscritas.component';
import { ReporteDeGananciasAnunciantesComponent } from '../reporte-de-ganancias-anunciantes/reporte-de-ganancias-anunciantes.component';
import { ReporteDeRevistasMasComentadassComponent } from '../reporte-de-revistas-mas-comentadas/reporte-de-revistas-mas-comentadas.component';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReporteDeRevistasMasSuscritassComponent,
    ReporteDeRevistasMasComentadassComponent,
    ReporteDeAnunciosCompradosComponent,
    ReporteDeGananciasAnunciantesComponent
  ],
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css'],
})
export class ReportesAdministradorComponent {

  panelOpciones = false;
  panelReportesAnunciosComprados = false;
  panelReportesGananciasAnunciantes = false;
  panelReportesRevistasMasComentadas = false;
  panelReportesRevistasMasGustadas = false;
  panelGrande: boolean = true;

  abrirPanel() {
    this.panelOpciones = !this.panelOpciones;
    this.panelGrande = !this.panelGrande;
  }

  abrirPanelReportesComentarios() {
    this.panelReportesAnunciosComprados = true;
    this.panelReportesRevistasMasComentadas = false;
    this.panelReportesGananciasAnunciantes = false;
    this.panelReportesRevistasMasGustadas = false;
    this.panelOpciones = false;
    this.panelGrande = true;
  }

  abrirPanelpanelReportesRevistasMasComentadas() {
    this.panelReportesAnunciosComprados = false;
    this.panelReportesGananciasAnunciantes = false;
    this.panelReportesRevistasMasGustadas = false;
    this.panelReportesRevistasMasComentadas = true;
    this.panelOpciones = false;
    this.panelGrande = true;
  }

  abrirPanelReportesGananciasAnunciantes() {
    this.panelReportesAnunciosComprados = false;
    this.panelReportesRevistasMasGustadas = false;
    this.panelReportesRevistasMasComentadas = false;
    this.panelReportesGananciasAnunciantes = true;
    this.panelOpciones = false;
    this.panelGrande = true;
  }

  abrirPanelReportesRevistasMasGustadas() {
    this.panelReportesAnunciosComprados = false;
    this.panelReportesRevistasMasGustadas = true;
    this.panelReportesRevistasMasComentadas = false;
    this.panelReportesGananciasAnunciantes = false;
    this.panelOpciones = false;
    this.panelGrande = true;
  }
}
