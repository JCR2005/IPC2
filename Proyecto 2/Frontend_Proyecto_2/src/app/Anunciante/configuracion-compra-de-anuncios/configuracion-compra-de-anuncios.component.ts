import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

import jwtDecode from 'jwt-decode'; // Asegúrate de tener instalada esta librería

import { CompraAnuncioService } from '../../services/servicioCompraAnuncio/compra-anuncio.service';

@Component({
  selector: 'app-configuracion-compra-de-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './configuracion-compra-de-anuncios.component.html',
  styleUrls: ['./configuracion-compra-de-anuncios.component.css'],
})
export class ConfiguracionCompraDeAnunciosComponent {
  tipoAnuncio: string = '';
  vigencia: string = '';
  anuncioTexto: string = '';
  anuncioVideo: string = '';
  fechaPublicacion: string = "";
  precio: number = 0;
  errorMessage: string = '';
  mensajeExito: boolean = false;
  saldo: number = 500;
  creditoDisponible: number = 500;
  seleccionoTipoAnuncio = false;
  seleccionoVigencia = false;
  imagenSeleccionada: File | null = null; // Variable para almacenar la imagen seleccionada
  imagenVisualizacion: string = '';

  constructor(
    private sanitizer: DomSanitizer,
    private compraAnuncioService: CompraAnuncioService
  ) {}

  actualizarPrecio() {
    const precioBase = this.getPrecioBase(this.tipoAnuncio);
    const multiplicador = this.getMultiplicador(this.vigencia);
    this.precio = precioBase * multiplicador;

    if (this.precio > this.creditoDisponible) {
      this.errorMessage = 'Crédito insuficiente para realizar la compra.';
    } else {
      this.errorMessage = '';
    }

    this.cambiar();
    this.cambiarLabelVigencia();
  }

  cambiar() {
    this.seleccionoTipoAnuncio = true;
    this.imagenSeleccionada = null;
    this.imagenVisualizacion="";
    this.anuncioTexto = '';
    this.anuncioVideo = '';
    this.imagenVisualizacion = '';
    this.precio = 0;
  }

  cambiarLabelVigencia() {
    this.seleccionoVigencia = true;
  }

  getPrecioBase(tipo: string): number {
    switch (tipo) {
      case 'texto':
        return 10;
      case 'imagen':
        return 100;
      case 'video':
        return 150;
      default:
        return 0;
    }
  }

  getMultiplicador(vigencia: string): number {
    switch (vigencia) {
      case '1':
        return 1;
      case '3':
        return 3;
      case '14':
        return 14;
      case '28':
        return 28;
      default:
        return 1;
    }
  }

  reiniciarCampos() {
    this.imagenSeleccionada = null;
    this.imagenVisualizacion="";
    this.anuncioTexto = '';
    this.anuncioVideo = '';
    this.tipoAnuncio = '';
    this.vigencia = '';
    this.fechaPublicacion="";
  }

  usuario:string="";

  onSubmit() {
    if (this.precio > this.creditoDisponible) {
      this.errorMessage = 'Crédito insuficiente para realizar la compra.';
      return;
    }


    const token = sessionStorage.getItem('token');

    if (token) {
      try {
        const payload = this.parseJwt(token);
        // Verifica si el tipo de cuenta es 'Administrador'
        this.usuario=payload.usuario;
      } catch (error) {

      }
    }
    const anuncio = {
      usuario:this.usuario,
      tipoAnuncio: this.tipoAnuncio,
      vigencia: this.vigencia,
      precio: this.precio,
      fechaPublicacionTexto:this.fechaPublicacion  ,
      anuncioTexto: this.anuncioTexto,
      anuncioVideo: this.anuncioVideo,
    };

    // Crear un FormData para enviar los datos
    const formData = new FormData();
    formData.append('anuncio', JSON.stringify(anuncio));

    // Agregar la imagen seleccionada al FormData
    if (this.imagenSeleccionada) {
      formData.append('imagen', this.imagenSeleccionada); // Añadir la imagen seleccionada
    }

    // Enviar la solicitud HTTP al backend
    this.compraAnuncioService.realizarCompra(formData).subscribe(
      (response: any) => {
        alert('Enviado correctamente: ' + response.message);
        this.reiniciarCampos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );
  }

  goBack() {
    window.history.back();
  }

  public archivos: any = [];

  onFileChange(event: any) {
    const imagen = event.target.files[0];

    // Almacena la imagen seleccionada en la variable
    this.imagenSeleccionada = imagen;

    this.extraerBase64(imagen).then((imagenb: any) => {
      this.imagenVisualizacion = imagenb.base;
    });
    this.archivos.push(imagen);
  }

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
