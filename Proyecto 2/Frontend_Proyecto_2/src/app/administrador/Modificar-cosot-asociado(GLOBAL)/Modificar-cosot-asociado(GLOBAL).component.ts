import { RevistasService } from './../../services/serviciosAdministraciòn/Revistas/revistas.service';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';



@Component({
  selector: 'app-recargar-cartera',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './Modificar-cosot-asociado(GLOBAL).component.html',
  styleUrl: './Modificar-cosot-asociado(GLOBAL).component.css'
})
export class classModificarCosotAsociadoGLOBALComponent {

  costoGlobalAsociado: number= 0.00;
  costoNuevo: number=0.00;
  numeroCartera: string="";
  usuario: string ="";

  constructor(private RevistasService: RevistasService) {}
  ngOnInit(): void {
    this.obtenerDatos();
  }

  //metodo para obetener datos de la cartera del usuario
  obtenerDatos(){

    this.RevistasService.obtenerCostoAsociadoGlobal().subscribe(
      (response: any) => {

        this.costoGlobalAsociado=response.costosGlobales.costo;


      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }


  racargarCartera(){
    const formData: FormData = new FormData();
   
    formData.append('costoAsociado', this.costoNuevo.toString());

    this.RevistasService.actualizacionCostoAsociadoGlobal(formData).subscribe(
      (response: any) => {
        alert(response.mensaje)
        this.costoNuevo=0;
        this.obtenerDatos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }


}
