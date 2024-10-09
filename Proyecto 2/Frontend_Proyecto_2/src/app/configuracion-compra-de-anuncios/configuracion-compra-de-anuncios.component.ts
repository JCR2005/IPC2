import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpClient } from '@angular/common/http'; // Importa HttpClientModule y HttpClient

@Component({
  selector: 'app-configuracion-compra-de-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule], // Agrega HttpClientModule aquí
  templateUrl: './configuracion-compra-de-anuncios.component.html',
  styleUrls: ['./configuracion-compra-de-anuncios.component.css'],
})
export class ConfiguracionCompraDeAnunciosComponent {
  tipoAnuncio: string = '';
  vigencia: string = '';
  anuncioTexto: string = '';
  fechaPublicacion: string = '';
  precio: number = 0;
  errorMessage: string = '';
  mensajeExito: boolean = false;
  saldo: number = 50;
  cartera: string = '0000';
  creditoDisponible: number = 50;
  seleccionoTipoAnuncio = false;
  seleccionoVigencia=false;

  constructor(private http: HttpClient) {}  // Inyectar HttpClient aquí

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

  onSubmit() {
    if (this.precio > this.creditoDisponible) {
      this.errorMessage = 'Crédito insuficiente para realizar la compra.';
      return;
    }

    // Ejemplo de datos para enviar al backend
    const datosCompra = {
      tipoAnuncio: this.tipoAnuncio,
      vigencia: this.vigencia,
      anuncioTexto: this.anuncioTexto,
      precio: this.precio,
      fechaPublicacion: this.fechaPublicacion,
      cartera: this.cartera,
    };

    // Enviar la solicitud HTTP al backend
    this.http.post('http://localhost:8080/api/v1/solicitudes', datosCompra)
      .subscribe(
        response => {
          console.log('Compra exitosa:', response);
          this.mensajeExito = true;
        },
        error => {
          console.error('Error al comprar el anuncio:', error);
          this.errorMessage = 'Error al procesar la compra.';
        }
      );
  }

  goBack() {
    window.history.back();
  }

  onFileChange(event: any) {
    // Maneja el cambio de archivo
  }

  onVideoChange(event: any) {
    // Maneja el cambio de video
  }

  entradaAnuncio() {
    // Puedes manejar la lógica de visualización de campos aquí si es necesario
  }
}
