declare var YT: any;

import { Component, OnInit, OnDestroy, Renderer2, ElementRef } from '@angular/core';
import { ProcesoAnunciosService } from '../../services/serviciosProcesos/procesoAnuncios/proceso-anuncios.service';
import { Anuncio } from '../../models/anuncio.model';
import { Router } from '@angular/router';
@Component({
  standalone: true,
  selector: 'app-anuncio-video',
  templateUrl: './anuncio-video.component.html',
  styleUrls: ['./anuncio-video.component.css']
})
export class AnuncioVideoComponent implements OnInit, OnDestroy {
  anuncios: string[] = [];
  anuncioActual: string = '';
  indiceActual: number = 0;
  player: any;
  anunciosVideo:Anuncio[]=[];
  currentUrl: string = '';
  constructor(private router: Router,private ProcesoAnunciosService: ProcesoAnunciosService,private renderer: Renderer2, private el: ElementRef) {}

  ngOnInit() {
    this.ProcesoAnunciosService.obtenerAnunciosVideo().subscribe(
      (response: any) => {
        this.anuncios = response.direcciones;
        this.anunciosVideo=response.anunciosVideo;
        this.anuncios = this.anuncios.map(url => this.convertirUrl(url));

        // Selecciona un anuncio aleatorio al inicio
        this.indiceActual = Math.floor(Math.random() * this.anuncios.length);
        this.anuncioActual = this.anuncios[this.indiceActual];
        
        this.cargarReproductor();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }


  ngOnDestroy(): void {
    if (this.player) {
      this.player.destroy(); // Destruye el reproductor al salir
    }
  }

  cambiarAnuncio(): void {
    // Genera un índice aleatorio dentro del rango de anuncios
    this.indiceActual = Math.floor(Math.random() * this.anuncios.length);
    this.anuncioActual = this.anuncios[this.indiceActual];
    this.player.loadVideoById(this.extraerId(this.anuncioActual));

    // Crear el objeto con los detalles del anuncio
    const historialEfectividadAnuncios = {
      usuario:  this.anunciosVideo[this.indiceActual].usuario,
      idAnuncio: this.anunciosVideo[this.indiceActual].idAnuncio,
      tipoAnuncio:  this.anunciosVideo[this.indiceActual].tipoAnuncio,
      ruta: this.currentUrl,
      fecha:  this.anunciosVideo[this.indiceActual].fechaPublicacion,
    };

    // Enviar los datos del anuncio
    this.ProcesoAnunciosService.enviarReporte(historialEfectividadAnuncios).subscribe(
      (response: any) => {
        // Puedes agregar alguna acción adicional aquí si es necesario
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }


  convertirUrl(url: string): string {
    const regex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^\/]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})/;
    const match = url.match(regex);
    return match && match[1] ? `https://www.youtube.com/embed/${match[1]}?autoplay=1&mute=1` : url;
  }

  extraerId(url: string): string {
    const regex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^\/]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})/;
    const match = url.match(regex);
    return match ? match[1] : '';
  }

  cargarReproductor(): void {
    if (typeof YT !== 'undefined' && YT.Player) {
      this.player = new YT.Player('player', {
        videoId: this.extraerId(this.anuncioActual),
        playerVars: {
          controls: 0,
          autoplay: 1,
          mute: 1,
          disablekb: 1,
          modestbranding: 1,
          fs: 0,
          rel: 0
        },
        events: {
          'onReady': this.onPlayerReady.bind(this),
          'onStateChange': this.onPlayerStateChange.bind(this)
        },
        origin: 'http://localhost:4200'
      });
      this.renderer.setStyle(this.el.nativeElement.querySelector('#player'), 'pointer-events', 'none');
    } else {
      console.log('La API de YouTube no está disponible aún.');
    }
  }

  onPlayerReady(event: any): void {
    event.target.mute();
    event.target.playVideo();
  }

  onPlayerStateChange(event: any): void {
    if (event.data === YT.PlayerState.ENDED) {
      this.cambiarAnuncio();  // Cambia al siguiente video sin recrear el reproductor
    } else if (event.data === YT.PlayerState.PAUSED) {
      event.target.playVideo();
    }
  }
}
