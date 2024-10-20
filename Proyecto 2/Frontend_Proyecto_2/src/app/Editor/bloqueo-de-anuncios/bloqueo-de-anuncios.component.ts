import { ListaDeRevistasService } from './../../services/servicioAprobacionDeRevistas/lista-de-revistas.service';
import { Revista } from './../../models/revista.model';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { token } from '../../token';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-bloqueo-de-anuncios',
  standalone: true,
  imports: [FormsModule,RouterModule, CommonModule],
  templateUrl: './bloqueo-de-anuncios.component.html',
  styleUrl: './bloqueo-de-anuncios.component.css'
})
export class BloqueoDeAnunciosComponent {
  revistas: Revista[] = [];
  usuario: string = "";
  precio:number=0;

  panelBloqueo: boolean = false;
  cargando: boolean = false;
  Procesando: boolean = false; // Nueva propiedad para el estado de carga
  respuesta: boolean = false;

  revistaEnproceso:string="";
  mensajeErrorVigencia: boolean = false;
  costoOcultacion:number=0;
  vigencia:number=0;
  fecha:string="";


  //varibles de mensaje
  procesoExitoso: boolean = false;
  panelMensaje: boolean = false;
  mensaje: string = "";
  listaRevistas:boolean=true;

  constructor(
    private ListaDeRevistasService: ListaDeRevistasService,
    private token: token
  ) {}


  mostrarPanelBloqueo(idRevista: string, costoOcultacion: number) {

    this.reiniciarDatos();
    this.revistaEnproceso=idRevista;
    this.panelBloqueo=true;

    this.costoOcultacion=costoOcultacion;
  }

  reiniciarDatos(){
    this.precio=0;
    this.vigencia=0;
    this.fecha="";
  }

  cerrarPanelBloqueo(){
    this.panelBloqueo=false;

  }
  modificarPrecios(event: any): void {
    const vigencia = event.target.value;
    this.precio=vigencia*this.costoOcultacion;
    // Aquí puedes ejecutar la lógica que desees con la fecha seleccionada.
  }
  onSubmit(){
    this.cargando = true;
    this.panelBloqueo=false;
    this.listaRevistas=false;
    const formData = new FormData();
    formData.append('idRevista', this.revistaEnproceso);
    formData.append('fecha', this.fecha);
    formData.append('vigencia', this.vigencia.toString());

    this.ListaDeRevistasService.bloquearAdds(formData).subscribe(
      (response: any) => {
        this.validaciones(response);
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
          this.panelMensaje=true;
        }, 1000);


      },
      (error) => {

      }
    );
  }
  ngOnInit(): void {
    this.cargarDatos();
  }

  validaciones(response :any){

  this.procesoExitoso=response.procesoExitoso;
  this.mensaje=response.mensaje;
  }

  cerrarPanelMensaje(){
    this.panelMensaje=false;

    if(this.procesoExitoso){
      this.listaRevistas=true;
      this.cargarDatos();
    }else{
      this.panelBloqueo=true;
    }

  }
  cargarDatos() {
    this.cargando = true; // Iniciar la carga
    const token = sessionStorage.getItem('token');
    if (token) {
      try {
        const payload = this.token.parseJwt(token);
        this.usuario = payload.usuario;
      } catch (error) {
        console.error('Error al parsear el token:', error);
      }
    }
    const formData = new FormData();
    formData.append('usuario',this.usuario);
    this.ListaDeRevistasService.listaRevistasConAdds(formData).subscribe(
      (response: Revista[]) => {
        this.revistas = response;
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        setTimeout(() => {
          this.cargando = false; // Terminar la carga después de 500ms
        }, 1000);
      }
    );
  }
}
