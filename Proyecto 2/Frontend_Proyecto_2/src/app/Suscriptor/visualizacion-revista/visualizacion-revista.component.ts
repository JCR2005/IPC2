import { ComentarService } from './../../services/serviciosSuscriptor/servicioComentar/comentar.service';
import { articulo } from './../../models/articulo.model';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { VisualizacionRevistaService } from './../../services/serviciosSuscriptor/ServicioVisualizacionRevista/visualizacion-revista.service';
import { RouterModule } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { Revista } from '../../models/revista.model';
import { token } from '../../token';
import { Comentario } from '../../models/comentario.model';
import { AnuncioVideoComponent } from '../../ComponentesComunes/anuncio-video/anuncio-video.component';
import { AnuncioImagenComponent } from '../../ComponentesComunes/anuncio-Imagen/anuncio-imagen.component';
import { AnuncioTextoComponent } from '../../ComponentesComunes/anuncio-texto/anuncio-texto.component';


@Component({
  selector: 'app-visualizacion-revista',
  standalone: true,
  imports: [CommonModule,AnuncioVideoComponent,AnuncioImagenComponent,AnuncioTextoComponent,FormsModule, FormsModule, RouterModule],
  templateUrl: './visualizacion-revista.component.html',
  styleUrls: ['./visualizacion-revista.component.css'] // Corregido de 'styleUrl' a 'styleUrls'
})

export class VisualizacionRevistaComponent implements OnInit {
  // Paneles
  perimisoAdds=false;
  permisoLikes=false;
  panelComentarios: boolean = false;
  panelArticulos: boolean = false;
  panelInformacionArticulo: boolean = false;
  panelVisualizarArticulo: boolean = false;
  panelSuscripcion: boolean = false;
  panelMensaje: boolean = false;

  // Datos del artículo y la revista
  fotoBase64: string | undefined;
  ArticuloEnProceso: articulo = {} as articulo;
  Revista: Revista = {} as Revista;

  // Otras variables
  cantidadLikes=0;
  mensajeRecibido: string = '';
  cargando: boolean = false;
  pdfVisualizacion: SafeResourceUrl | undefined;
  articulos: articulo[] = [];
  comentarios: Comentario[] = [];
  procesoExitoso: boolean = false;
  mensaje: string = '';
  fechaSuscripcion: string = '';
  usuario: string = '';
  comentario:string="";

  constructor(
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private VisualizacionRevistaService: VisualizacionRevistaService,
    private token:token,
    private ComentarService:ComentarService
  ) {}

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

  cerrarArticulo() {
    this.panelVisualizarArticulo = false;
    this.panelArticulos = true;
    this.panelInformacionArticulo = false;
  }

  abrirPanelComentarios() {

    this.panelComentarios = !this.panelComentarios;
  }

  cargarDatos() {
    const formData = new FormData();
    formData.append('idRevista', this.mensajeRecibido);

    this.cargando = true;
    this.VisualizacionRevistaService.listarArticulos(formData).subscribe(
      (response: any) => {
        this.Revista = response.revista;
        this.articulos = response.articulos;
        this.cargando = false;
        this.panelArticulos = true;
        this.perimisoAdds=response.anuncios;
        this.permisoLikes=this.Revista.likes
        this.verComentarios();
        this.obtenerLikes();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }

  visualisarArticulo(idArticulo: number) {
    const formData = new FormData();
    formData.append('idArticulo', idArticulo.toString());

    this.cargando = true;
    this.VisualizacionRevistaService.traerArtiiculo(formData).subscribe(
      (response: any) => {
        this.cargando = false;
        this.panelArticulos = false;
        this.panelInformacionArticulo = true;
        this.asignarDatos(response);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }

  asignarDatos(response: any) {
    this.ArticuloEnProceso = response.articulo;

    if (response.articulo && response.articulo.pdf) {
      this.panelVisualizarArticulo = true;
      this.pdfVisualizacion = this.sanitizer.bypassSecurityTrustResourceUrl(
        `data:application/pdf;base64,${response.articulo.pdf}`
      );
    }
  }


obtenerLikes(){
  const formData = new FormData();
  formData.append('idRevista', this.mensajeRecibido);

  this.cargando = true;
  this.VisualizacionRevistaService.obtenerLikesRevista(formData).subscribe(
    (response: any) => {
    this.cantidadLikes=response.cantidadLikes;
    },
    (error) => {
      console.error('Error al enviar los datos:', error);
    }
  );

}

  comentar(){
    this.token.obtenerUsuario();

    const comentario={
      idRevista:this.mensajeRecibido,
      idUsuario: this.token.obtenerUsuario(),
      comentario:this.comentario
    }

    this.ComentarService.comentar(comentario).subscribe(
      (response: any) => {
        this.verComentarios();
        this.comentario="";
      },
      (error) => {
        console.error('Error al enviar los datos:', error);

      }
    );

  }

  darMeGusta(){
    this.token.obtenerUsuario();

    const MeGusta={
      idRevista:this.mensajeRecibido,
      idUsuario: this.token.obtenerUsuario()
    }

    this.VisualizacionRevistaService.darMeGusta(MeGusta).subscribe(
      (response: any) => {

        this.obtenerLikes();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);

      }
    );

  }
    verComentarios(){
      this.token.obtenerUsuario();
      const formData = new FormData();
      formData.append('idRevista', this.mensajeRecibido);
      this.ComentarService.comentariosRevista(formData).subscribe(
        (response: any) => {
         this.comentarios=response.comentarios;

        },
        (error) => {
          console.error('Error al enviar los datos:', error);

        }
      );


  }
}
