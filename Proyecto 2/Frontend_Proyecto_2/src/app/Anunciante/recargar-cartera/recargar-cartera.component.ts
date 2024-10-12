import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { carteraService } from '../../services/servicoCartera/cartera.service';
import { token } from '../../token';

@Component({
  selector: 'app-recargar-cartera',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './recargar-cartera.component.html',
  styleUrl: './recargar-cartera.component.css'
})
export class RecargarCarteraComponent {

  saldo: number= 0.00;
  recarga: number=0.00;
  numeroCartera: string="";
  usuario: string ="";

  constructor(private carteraService: carteraService, private token: token) {}
  ngOnInit(): void {
    this.obtenerDatos();
  }

  //metodo para obetener datos de la cartera del usuario
  obtenerDatos(){
    const formData: FormData = new FormData();
    const token = sessionStorage.getItem('token');

    if (token) {
      try {
        const payload = this.token.parseJwt(token);
        // Verifica si el tipo de cuenta es 'Administrador'
        this.usuario=payload.usuario;
      } catch (error) {

      }
    }


    formData.append('usuario', this.usuario);

    this.carteraService.obtenerCartera(formData).subscribe(
      (response: any) => {
        this.numeroCartera=response.cartera;
        this.saldo=response.saldo;

      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }


  racargarCartera(){
    const formData: FormData = new FormData();
    const usuario={
      usuario:this.usuario,
      idCartera:this.numeroCartera
    }

    formData.append('usuario', JSON.stringify(usuario));
    formData.append('recarga', this.recarga.toString());

    this.carteraService.RecargarCartera(formData).subscribe(
      (response: any) => {
        alert(response.mensaje)
        this.recarga=0;
        this.obtenerDatos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }


}
