import { DuracionAnuncioService } from './../services/servicioDuracionAnuncio/duracion-anuncio.service';
  import { Component } from '@angular/core';
  import { FormsModule } from '@angular/forms';
   import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configuracion-duracion-anuncios',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './configuracion-duracion-anuncios.component.html',
  styleUrl: './configuracion-duracion-anuncios.component.css'
})
export class ConfiguracionDuracionAnunciosComponent {

  constructor(private DuracionAnuncioService: DuracionAnuncioService) {}
  duracion1: number  = 0;
  duracion2: number  = 0;
  duracion3: number  = 0;
  duracion4: number  = 0;

  reiniciarDatos(){
    this.duracion1  = 0;
    this.duracion2  = 0;
    this.duracion3  = 0;
    this.duracion4  = 0;

  }
  onSubmit() {


    const formData = [
      {
        id_vigencia: "v_1",
        vigencia: this.duracion1,
      },
      {
        id_vigencia: "v_2",
        vigencia: this.duracion2,
      },
      {
        id_vigencia: "v_3",
        vigencia: this.duracion3
        ,
      },
      {
        id_vigencia: "v_4",
        vigencia: this.duracion4
        ,
      }
    ];
    this.DuracionAnuncioService.cambiarDuracion(formData).subscribe(
      (response: any) => {
        alert("Enviado correctamente: " + response.message);
        this.reiniciarDatos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );


  }


}
