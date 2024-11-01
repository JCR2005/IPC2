
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { Revista } from '../../models/revista.model';
import { SuscripcionRevistasService } from '../../services/serviciosSuscriptor/ServicioSuscripcion/suscripcion-revistas.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { token } from '../../token';
import { RevistasSuscritasComponent } from '../revistas-suscritas/revistas-suscritas.component';



@Component({
  selector: 'app-pagina-principal-suscriptor',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterModule,MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatNativeDateModule,RevistasSuscritasComponent],
  templateUrl: './pagina-principal-suscriptor.component.html',
  styleUrl: './pagina-principal-suscriptor.component.css'
})
export class PaginaPrincipalSuscriptorComponent {

  cargando:boolean=false;
  panelRevistas:boolean=false;
  revistas: Revista[] = [];
  panelSuscripcion:boolean=false;
  panelMensaje:boolean=false;
  procesoExitoso:boolean=false;
  mensaje:string="";
  revistaEnProceso:Revista={} as Revista;
  fechaSuscripcion:string="";
  usuario:string="";

  constructor(private location: Location,private token:token, private router: Router, private SuscripcionRevistasService:SuscripcionRevistasService) {}

  ngOnInit() {
    this.preventBackNavigation();
    this.cargarDatos();
  }

  cargarDatos() {
    this.token.obtenerUsuario()
    const formData = new FormData();
    formData.append('usuario',"prueba 0.2" );
    this.cargando=true;
    this.SuscripcionRevistasService.listarRevistas(formData).subscribe(
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

  obtenerFechaLegible(timestamp: string): string {
    const fecha = new Date(timestamp);
    return fecha.toLocaleDateString();
  }


  mostraPanelConfirmacionSuscripcion(revistaEnProceso:Revista){
    this.panelSuscripcion=true;
    this.revistaEnProceso=revistaEnProceso;

  }

  ocultarPanelConfirmacionSuscripcion(){
    this.panelSuscripcion=false;
    this.revistaEnProceso={}as Revista;

  }

  cerrarPanelMensaje(){
    this.panelMensaje=false;
    if(this.procesoExitoso===true){
      this.fechaSuscripcion="";
      this.panelSuscripcion=false;
      this.cargarDatos();
      return
    }
    this.panelSuscripcion=true;

  }

 
  suscribirse(){
    this.cargando=true;
    this.token.obtenerUsuario()
    const Suscripciòn = {
      idUsuario: "prueba 0.2",
      idRevista:this.revistaEnProceso.idRevista,
      fechaSuscripcionTexto:this.fechaSuscripcion

    };
    this.SuscripcionRevistasService.suscribirRevista(Suscripciòn).subscribe(
      (response: any) => {
        this.mensaje=response.mensaje;
        this.procesoExitoso=response.procesoExitoso;
        this.cargando=false;
        this.panelMensaje=true;


          this.panelSuscripcion=false;



      },
      (error) => {
        console.error('Error al enviar los datos:', error);
      }
    );

  }

  preventBackNavigation() {
    history.pushState(null, '', location.href);

    window.addEventListener('popstate', () => {
      history.pushState(null, '', location.href); // Empuja un nuevo estado al historial
      this.router.navigate(["/paginaPrincipalAdministrador"]);

    });
  }
}
