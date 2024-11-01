import { VisualizacionRevistaService } from './../../services/serviciosSuscriptor/ServicioVisualizacionRevista/visualizacion-revista.service';
import { Component } from '@angular/core';
import { Revista } from '../../models/revista.model';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';



import { token } from '../../token';

@Component({
  selector: 'app-revistas-suscritas',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './revistas-suscritas.component.html',
  styleUrl: './revistas-suscritas.component.css'
})
export class RevistasSuscritasComponent {
  cargando:boolean=false;
  panelRevistas:boolean=true;
  revistas: Revista[] = [];
  panelSuscripcion:boolean=false;
  panelMensaje:boolean=false;
  procesoExitoso:boolean=false;
  mensaje:string="";
  revistaEnProceso:Revista={} as Revista;
  fechaSuscripcion:string="";
  usuario:string="";
  constructor(private Router:Router,private token:token, private VisualizacionRevistaService:VisualizacionRevistaService) {}

  ngOnInit() {

    this.cargarDatos();
  }

  obtenerFechaLegible(timestamp: string): string {
    const fecha = new Date(timestamp);
    return fecha.toLocaleDateString();
  }

  cargarDatos() {
    this.token.obtenerUsuario()
    const formData = new FormData();
    formData.append('usuario',"prueba 0.2" );
    this.cargando=true;
    this.VisualizacionRevistaService.listarRevistas(formData).subscribe(
      (response: any) => {
        this.revistas = response.revistas;
        this.cargando=false;
        this.panelRevistas=true;
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );
  }


  abrirRevista(revista: string) {
    this.Router.navigate(['/revista'], { queryParams: { revista } });
  }
}
