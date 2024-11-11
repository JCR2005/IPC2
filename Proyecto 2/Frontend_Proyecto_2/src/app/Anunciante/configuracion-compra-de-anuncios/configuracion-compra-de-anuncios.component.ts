import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';
import jwtDecode from 'jwt-decode'; // Asegúrate de tener instalada esta librería

import { CompraAnuncioService } from '../../services/servicioCompraAnuncio/compra-anuncio.service';
import { Vigencia } from './../../models/vigencia.model';

/**
 * Componente para la configuración de compra de anuncios.
 * Permite calcular el precio de un anuncio en función de su tipo y vigencia.
 */
@Component({
  selector: 'app-configuracion-compra-de-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './configuracion-compra-de-anuncios.component.html',
  styleUrls: ['./configuracion-compra-de-anuncios.component.css'],
})
export class ConfiguracionCompraDeAnunciosComponent implements OnInit {

  // Propiedades relacionadas con los precios y cálculos
  precioTexto: number = 0;
  precioImagen: number = 0;
  precioVideo: number = 0;
  precioTotal: number = 0;  // Precio total calculado basado en la selección
  multiplicador: number = 0; // Multiplicador basado en la vigencia seleccionada

  // Variables para verificar si el tipo y la vigencia están seleccionados
  seleccionoTipoAnuncio: boolean = false;
  seleccionoVigencia: boolean = false;
  vigencias: Vigencia[] = []; // Lista de opciones de vigencia
  tipoAnuncio: string = ''; // Tipo de anuncio (texto, imagen, video)
  vigencia: string = ''; // Vigencia seleccionada
  anuncioTexto: string = ''; // Texto del anuncio
  anuncioVideo: string = ''; // Video del anuncio
  fechaPublicacion: string = ''; // Fecha de publicación
  precio: number = 0; // Precio total de la compra
  errorMessage: string = ''; // Mensaje de error
  mensajeExito: boolean = false; // Mensaje de éxito
  saldo: number = 500; // Saldo disponible del usuario
  creditoDisponible: number = 500; // Crédito disponible para realizar la compra

  // Variables para manejar la imagen seleccionada
  imagenSeleccionada: File | null = null;
  imagenVisualizacion: string = '';

  // Variable para almacenar el usuario actual
  usuario: string = '';

  constructor(
    private sanitizer: DomSanitizer,
    private compraAnuncioService: CompraAnuncioService
  ) {}

  /**
   * ngOnInit se ejecuta al inicializar el componente.
   * Aquí se cargan los precios de los anuncios y las vigencias desde el servicio.
   */
  ngOnInit(): void {
    this.compraAnuncioService.obtenerPrecios().subscribe(
      (response: any) => {
        // Asigna los precios y las vigencias recibidas desde el backend
        this.vigencias = response.vigencia;
        this.precioTexto = response.precioTexto;
        this.precioVideo = response.preciovideoo;
        this.precioImagen = response.precioImagen;
      },
      (error) => {
        console.error('Error al cargar los precios:', error);
        alert('Error al cargar los datos de precios.');
      }
    );
  }

  /**
   * Calcula el precio total del anuncio en base al tipo y vigencia seleccionados.
   */
  calcularPrecio() {
    if (this.tipoAnuncio && this.vigencia) {
      let precioBase = 0;

      // Determina el precio base según el tipo de anuncio
      switch (this.tipoAnuncio) {
        case 'texto':
          precioBase = this.precioTexto;
          break;
        case 'imagen':
          precioBase = this.precioImagen;
          break;
        case 'video':
          precioBase = this.precioVideo;
          break;
        default:
          precioBase = 0;
          break;
      }

      // Obtiene el multiplicador de la vigencia seleccionada
      switch (this.vigencia) {
        case '1':
          this.multiplicador = this.vigencias[0].vigencia;
          break;
        case '2':
          this.multiplicador = this.vigencias[1].vigencia;
          break;
        case '3':
          this.multiplicador = this.vigencias[2].vigencia;
          break;
        case '4':
          this.multiplicador = this.vigencias[3].vigencia;
          break;
        default:
          this.multiplicador = 0;
          break;
      }

      // Calcula el precio total
      this.precioTotal = precioBase * this.multiplicador;
    } else {
      this.precioTotal = 0; // Si no hay selección de tipo o vigencia, el precio será 0
    }
  }

  /**
   * Resetea los campos del formulario, limpiando las selecciones y entradas.
   */
  reiniciarCampos() {
    this.imagenSeleccionada = null;
    this.imagenVisualizacion = '';
    this.anuncioTexto = '';
    this.anuncioVideo = '';
    this.tipoAnuncio = '';
    this.vigencia = '';
    this.fechaPublicacion = '';
  }

  /**
   * Al seleccionar un tipo de anuncio, actualiza el estado de la selección.
   */
  cambiar() {
    this.seleccionoTipoAnuncio = true;
    this.imagenSeleccionada = null;
    this.imagenVisualizacion = '';
    this.anuncioTexto = '';
    this.anuncioVideo = '';
    this.precio = 0;
  }

  /**
   * Al seleccionar una vigencia, actualiza el estado de la selección.
   */
  cambiarLabelVigencia() {
    this.seleccionoVigencia = true;
  }

  /**
   * Realiza la compra del anuncio. Valida el saldo disponible y envía los datos al backend.
   */
  onSubmit() {
    // Valida si el crédito disponible es suficiente para realizar la compra
    if (this.precio > this.creditoDisponible) {
      this.errorMessage = 'Crédito insuficiente para realizar la compra.';
      return;
    }

    // Obtiene el token de sesión y decodifica la información del usuario
    const token = sessionStorage.getItem('token');
    if (token) {
      try {
        const payload = this.parseJwt(token);
        this.usuario = payload.usuario;
      } catch (error) {
        console.error('Error al decodificar el token JWT:', error);
      }
    }

    // Crea el objeto con los detalles del anuncio
    const anuncio = {
      usuario: this.usuario,
      tipoAnuncio: this.tipoAnuncio,
      vigencia: this.multiplicador,
      precio: this.precioTotal,
      fechaPublicacionTexto: this.fechaPublicacion,
      anuncioTexto: this.anuncioTexto,
      anuncioVideo: this.anuncioVideo,
    };

    // Crea un FormData para enviar los datos
    const formData = new FormData();
    formData.append('anuncio', JSON.stringify(anuncio));

    // Agrega la imagen seleccionada al FormData
    if (this.imagenSeleccionada) {
      formData.append('imagen', this.imagenSeleccionada);
    }

    // Enviar la solicitud HTTP al backend
    this.compraAnuncioService.realizarCompra(formData).subscribe(
      (response: any) => {
        // Resetea los campos y muestra un mensaje de éxito
        this.reiniciarCampos();
        alert('Compra realizada con éxito');
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );
  }

  /**
   * Regresa a la página anterior en el historial del navegador.
   */
  goBack() {
    window.history.back();
  }

  /**
   * Maneja el cambio de archivos seleccionados, en este caso imágenes.
   * Convierte la imagen seleccionada a una cadena base64 para vista previa.
   */
  public archivos: any = [];

  onFileChange(event: any) {
    const imagen = event.target.files[0];

    // Almacena la imagen seleccionada en la variable
    this.imagenSeleccionada = imagen;

    // Convierte la imagen a base64 para vista previa
    this.extraerBase64(imagen).then((imagenb: any) => {
      this.imagenVisualizacion = imagenb.base;
    });
    this.archivos.push(imagen);
  }

  /**
   * Convierte un archivo en base64 para vista previa.
   */
  extraerBase64 = ($event: any) => new Promise((resolve, reject) => {
    try {
      const unsafeImg = window.URL.createObjectURL($event);
      const image = this.sanitizer.bypassSecurityTrustUrl(unsafeImg);
      const reader = new FileReader();
      reader.readAsDataURL($event);
      reader.onload = () => {
        resolve({ base: reader.result });
      };
      reader.onerror = (error) => {
        resolve({ base: null });
      };
    } catch (e) {
      reject(null);
    }
  });

  /**
   * Decodifica el token JWT para obtener la información del usuario.
   */
  private parseJwt(token: string): any {
    try {
      const base64Url = token.split('.')[1];
      if (!base64Url) {
        throw new Error('Token mal formado');
      }
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64));
    } catch (error) {
      console.error('Error al decodificar el token JWT:', error);
      throw error;
    }
  }
}
