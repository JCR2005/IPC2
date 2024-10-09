import { CostoAnuncioService } from './../services/servicioCostoAnuncio/costo-anuncio.service';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  selector: 'app-config-precios-anuncios',
  templateUrl: './configuracion-costa-anuncio.component.html',
  styleUrls: ['./configuracion-costa-anuncio.component.css']
})
export class ConfigPreciosAnunciosComponent {

  precioTexto: number  = 0;
  precioImagen: number  = 0;
  precioVideo: number  = 0;

  reiniciarCampos(){
    this.precioTexto = 0;
    this.precioImagen = 0;
    this.precioVideo = 0;
  }

  constructor(private CostoAnuncioService: CostoAnuncioService) {}

  // Validación de los campos antes de enviar
  validarPrecios(): boolean {
    const esValidoTexto = this.precioTexto >= 0 && /^[0-9]+(\.[0-9]+)?$/.test(this.precioTexto.toString());
    const esValidoImagen = this.precioImagen >= 0 && /^[0-9]+(\.[0-9]+)?$/.test(this.precioImagen.toString());
    const esValidoVideo = this.precioVideo >= 0 && /^[0-9]+(\.[0-9]+)?$/.test(this.precioVideo.toString());

    if (!esValidoTexto) {
      alert('El precio de anuncio de texto no es válido.');
      return false;
    }

    if (!esValidoImagen) {
      alert('El precio de anuncio de imagen no es válido.');
      return false;
    }

    if (!esValidoVideo) {
      alert('El precio de anuncio de video no es válido.');
      return false;
    }

    return true;
  }

  onSubmit() {
    // Verificar si los valores son válidos antes de enviar
    if (!this.validarPrecios()) {
      return;  // Detener el envío si los valores no son válidos
    }

    const formData = [
      {
        id_Add: "add_texto",
        costo_Add: this.precioTexto,
      },
      {
        id_Add: "add_imagen",
        costo_Add: this.precioImagen,
      },
      {
        id_Add: "add_video",
        costo_Add: this.precioVideo,
      }
    ];

    this.CostoAnuncioService.registrarUsuario(formData).subscribe(
      (response: any) => {
        alert("Enviado correctamente: " + response.message);
        this.reiniciarCampos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );
  }
}
