import { LoginService } from './../../services/servicioLogin/login.service';
import { token } from './../../token';
import { Usuario } from '../../models/usuario.model';
import { ServicioPerfilService } from '../../services/serviciosComunes/servicioPerfil/servicio-perfil.service';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-editar-perfil',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './editar-perfil.component.html',
  styleUrl: './editar-perfil.component.css'
})


export class EditarPerfilComponent {

autenticacion:boolean=true;

panelConfirmacion:boolean=false;
panelDatos:boolean=true;
panelVerificacion:boolean=false;
procesoExitoso:boolean=false;

  showPassword = false;
  password: string = "";
  passwordNueva: string = "";
  nombre: string = "";
  descripcion: string = "";
  passwordValida = true;
  nombreValido = true;
  descripcionValida = true;
  cargando:boolean=false;
  usuario:string="";
  panelMensaje: boolean = false;
  mensaje: string = "";
  Usuario: Usuario = {} as Usuario;


  constructor(private Router:Router,private ServicioPerfilService: ServicioPerfilService,private token:token,private loginService:LoginService) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargando = true;

    const token = sessionStorage.getItem('token');
    if (token) {
      try {
        const payload = this.token.parseJwt(token);
        this.usuario = payload.usuario;
      } catch (error) {
        console.error('Error al parsear el token:', error);
      }
    }

    const Usuario = {
      usuario: this.usuario
    };

    this.ServicioPerfilService.obtenerPerfil(Usuario).subscribe(
      (response: any) => {
        this.validaciones(response);
          this.cargando = false; // Termina la carga


      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        this.cargando = false; // Termina la carga
      }
    );
  }

  validaciones(response: any) {
    if (response.usuarioEncontrado ) {
      this.asignarDatos(response);
      return;
    }
    this.mensaje=response.mensaje;
    this.panelMensaje=true;

  }

  asignarDatos(response: any) {
    this.Usuario = response.usuario;

    this.usuario = this.Usuario.usuario;

  }

  reiniciarDatos() {
    this.showPassword = false;
    this.passwordNueva = "";
    this.nombre = "";
    this.descripcion = "";
    this.passwordValida = true;
    this.nombreValido = true;
    this.descripcionValida = true;
  }

  onSubmit() {
    const credentials = {
      nombre: this.nombre,
      password: this.passwordNueva,
      descripcion:this.descripcion
    };
    this.ServicioPerfilService.VerificarDatos(credentials).subscribe(
      (response: any) => {
        this.passwordValida=response.contraceñaValida;
        this.nombreValido=response.nombreValido;
        this.descripcionValida=response.descripcionValida;
        this.verificarDatos();
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }
  verificarDatos(){
    console.log('Password Válida:', this.passwordValida);
    console.log('Nombre Válido:', this.nombreValido);
    console.log('Descripción Válida:', this.descripcionValida);

    if(this.passwordValida===true && this.nombreValido===true && this.descripcionValida===true){
      this.Confirmacion();
    }

  }
  Confirmacion(){
    this.panelConfirmacion=true;
    this.panelDatos=false;
  }

  cerrarConfirmacion(){
    this.panelConfirmacion=false;
    this.panelVerificacion=false;
    this.panelDatos=true;
  }

  verificarCambioContracena(){
    if(this.passwordNueva!=""){
      this.panelVerificacion=true;
      this.panelConfirmacion=false;
    }else{
      this.panelConfirmacion=false;
      this.cargando=true;
      this.enviarSolicitud();
    }
  }

  verificarIdentidad(){
    const credentials = {
      usuario: this.usuario,
      password: this.password
    };

    this.loginService.autenticacion(credentials).subscribe(
      (response: any) => {
      if(response.usuarioVaerificado===false){
        this.autenticacion=false;
      }else{
        this.panelVerificacion=false;
        this.cargando=true;
        this.enviarSolicitud();

      }


      },
      (error) => {
        console.error('Error de inicio de sesión:', error);
        alert('Error de inicio de sesión. Verifica tus credenciales.');
      }
    );

  }

  enviarSolicitud(){
    const formData = {
      usuario: this.usuario,
      password: this.passwordNueva,
      nombre: this.nombre,
      descripcion: this.descripcion,
    };

    this.ServicioPerfilService.editarPerfil(formData).subscribe(
      (response: any) => {

        this.procesoExitoso=response.datosActualizados;
        this.mensaje=response.mensaje;
        this.panelMensaje=true;
      },
      (error) => {
        console.error('Error al enviar los datos:', error);
        alert('Error al registrar: ' + error.message);
      }
    );

  }
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  cerrarPanelMensaje(){
    this.panelMensaje=false;

    if(this.procesoExitoso){
      this.Router.navigate(['/perfil']);

    }

  }
}
