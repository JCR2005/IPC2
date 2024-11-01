import { articulo } from './../../models/articulo.model';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { VisualizacionRevistaService } from './../../services/serviciosSuscriptor/ServicioVisualizacionRevista/visualizacion-revista.service';
import { RouterModule } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-visualizacion-revista',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './visualizacion-revista.component.html',
  styleUrl: './visualizacion-revista.component.css'
})

export class VisualizacionRevistaComponent {
  panelArticulos:boolean=false;

  fotoBase64: string | undefined; // Para almacenar la cadena base64
  ArticuloEnProceso:articulo={} as articulo;
  mensajeRecibido: string = '';
  cargando:boolean=false;
  pdfVisualizacion: SafeResourceUrl | undefined;

  articulos: articulo[] = [];
  panelSuscripcion:boolean=false;
  panelMensaje:boolean=false;
  procesoExitoso:boolean=false;
  mensaje:string="";

  fechaSuscripcion:string="";
  usuario:string="";
  constructor(private sanitizer: DomSanitizer,private route: ActivatedRoute,private VisualizacionRevistaService:VisualizacionRevistaService) {}

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.mensajeRecibido = params['revista'] || 'No se recibió ningún mensaje';
    });

    this.cargarDatos();
  }

  obtenerFechaLegible(timestamp: string): string {
    const fecha = new Date(timestamp);
    return fecha.toLocaleDateString();
  }

  cargarDatos() {

    const formData = new FormData();
    formData.append('idRevista',this.mensajeRecibido );
    this.cargando=true;
    this.VisualizacionRevistaService.listarArticulos(formData).subscribe(
      (response: any) => {
        this.articulos = response.articulos;
        this.cargando=false;
        this.panelArticulos=true;
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }


  visualisarArticulo(idArticulo:number){

    const formData = new FormData();
    formData.append('idArticulo',idArticulo.toString());
    this.cargando=true;
    this.VisualizacionRevistaService.traerArtiiculo(formData).subscribe(
      (response: any) => {

        this.cargando=false;
        this.panelArticulos=false;
        this.asignarDatos(response);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }
  asignarDatos(response: any) {
    this.ArticuloEnProceso = response.articulo;
    // Convertir Base64 a datos URL para mostrar
    if (response.articulo && response.articulo.pdf) {
      // Sanea el URL antes de asignarlo
      this.pdfVisualizacion = this.sanitizer.bypassSecurityTrustResourceUrl(
        `data:application/pdf;base64,${response.articulo.pdf}`
      );
    }
  }

  // Convertir Base64 a datos URL para mostrar




}
