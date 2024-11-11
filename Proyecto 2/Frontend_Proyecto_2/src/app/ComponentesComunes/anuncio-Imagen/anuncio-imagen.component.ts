import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProcesoAnunciosService } from '../../services/serviciosProcesos/procesoAnuncios/proceso-anuncios.service';
import { CommonModule } from '@angular/common'; // Importar CommonModule
import { Router } from '@angular/router';
import { Anuncio } from '../../models/anuncio.model';

@Component({
  selector: 'app-anuncio-imagen',
  standalone: true,
  imports: [CommonModule],  // Incluir CommonModule aquí
  templateUrl: './anuncio-imagen.component.html',
  styleUrl: './anuncio-imagen.component.css'
})
export class AnuncioImagenComponent implements OnInit, OnDestroy {
  anuncioActual: string = 'Espacio publicitario';
  imagenes: string[] = [];
  anuncios: Anuncio[]=[];
  currentImageIndex: number = 0; // Índice para la imagen actual
  intervalo: any;
  currentUrl: string = '';
  constructor(private procesoAnunciosService: ProcesoAnunciosService, private router: Router) {}

  ngOnInit() {
    this.currentUrl = this.router.url;
    this.procesoAnunciosService.obtenerAnuncioImagen().subscribe(
      (response: any) => {
        this.imagenes = response.imagenes; // Se reciben las imágenes codificadas en Base64
        this.anuncios=response.anunciosImagen;
        // Inicializamos el intervalo para cambiar la imagen cada 3 segundos
        this.intervalo = setInterval(() => {
          this.cambiarAnuncio();
        }, 30000);
      },
      (error) => {
        console.error('Error al obtener las imágenes:', error);
      }
    );
  }

  ngOnDestroy(): void {
    // Limpiamos el intervalo al destruir el componente
    if (this.intervalo) {
      clearInterval(this.intervalo);
    }
  }

  cambiarAnuncio(): void {
    if (this.imagenes.length > 0) {
      // Incrementa el índice y vuelve al inicio si se llega al final de la lista
      this.currentImageIndex = (this.currentImageIndex + 1) % this.imagenes.length;
    }

    // Crear el objeto con los detalles del anuncio
    const historialEfectividadAnuncios = {
      usuario: this.anuncios[this.currentImageIndex].usuario,
      idAnuncio: this.anuncios[this.currentImageIndex].idAnuncio,
      tipoAnuncio: this.anuncios[this.currentImageIndex].tipoAnuncio,
      ruta: this.currentUrl,
      fecha: this.anuncios[this.currentImageIndex].fechaPublicacion,
    };
    this.procesoAnunciosService.enviarReporte(historialEfectividadAnuncios).subscribe(
      (response: any) => {
        // Puedes agregar alguna acción adicional aquí si es necesario
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );

  }


}
