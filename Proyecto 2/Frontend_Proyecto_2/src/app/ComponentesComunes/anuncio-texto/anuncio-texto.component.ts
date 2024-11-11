import { Component } from '@angular/core';
import { ProcesoAnunciosService } from '../../services/serviciosProcesos/procesoAnuncios/proceso-anuncios.service';
import { Anuncio } from '../../models/anuncio.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-anuncio-texto',
  standalone: true,
  imports: [],
  templateUrl: './anuncio-texto.component.html',
  styleUrls: ['./anuncio-texto.component.css']
})
export class AnuncioTextoComponent {
  anuncioActual: string = 'Espacio publicitario';
  anuncios: string[] = [];
  listaDeAnuncios: Anuncio[] = [];
  intervalo: any;
  currentUrl: string = '';
  anunciosRestantes: Anuncio[] = []; // Lista para manejar los anuncios no repetidos

  constructor(
    private ProcesoAnunciosService: ProcesoAnunciosService,
    private router: Router
  ) {}

  ngOnInit() {
    this.currentUrl = this.router.url;

    // Obtener los anuncios
    this.ProcesoAnunciosService.obtenerAnunciosTexto().subscribe(
      (response: any) => {
        this.listaDeAnuncios = response.anunciosTexto;
        this.anunciosRestantes = [...this.listaDeAnuncios]; // Inicializamos la lista de anuncios restantes
        this.cambiarAnuncio(); // Mostrar un anuncio inmediatamente

        // Establecer el intervalo para cambiar anuncios periódicamente
        this.intervalo = setInterval(() => {
          this.cambiarAnuncio();
        }, 30000); // Cambiar cada 30 segundos
      },
      (error) => {
        console.error('Error al obtener los anuncios:', error);
      }
    );
  }

  ngOnDestroy(): void {
    // Limpiar el intervalo cuando el componente se destruya
    if (this.intervalo) {
      clearInterval(this.intervalo);
    }
  }

  cambiarAnuncio(): void {
    if (this.anunciosRestantes.length === 0) {
      // Si no hay anuncios restantes, barajamos la lista
      this.anunciosRestantes = [...this.listaDeAnuncios];
    }

    // Seleccionamos un anuncio aleatorio de los restantes
    const indice = Math.floor(Math.random() * this.anunciosRestantes.length);
    const anuncioSeleccionado = this.anunciosRestantes.splice(indice, 1)[0]; // Eliminamos el anuncio seleccionado de los restantes
    this.anuncioActual = anuncioSeleccionado.anuncioTexto;
    this.anuncios.push(this.anuncioActual);

    // Crear el objeto con los detalles del anuncio
    const historialEfectividadAnuncios = {
      usuario: anuncioSeleccionado.usuario,
      idAnuncio: anuncioSeleccionado.idAnuncio,
      tipoAnuncio: anuncioSeleccionado.tipoAnuncio,
      ruta: this.currentUrl,
      fecha: anuncioSeleccionado.fechaPublicacion,
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
}
