import { Component, OnInit } from '@angular/core';
import { Usuario } from './../../models/usuario.model';
import { ServicioPerfilService } from './../../services/serviciosComunes/servicioPerfil/servicio-perfil.service';
import { token } from '../../token';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';


@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule,RouterModule], // Asegúrate de que esté aquí
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  panelMensaje: boolean = false;
  mensaje: string = "";
  panelLogout: boolean = false;
  nombre: string = "";
  usuario: string = "";
  tipoCuenta: string = "";
  edad: number = 0;
  descripcion: string = "";
  intereses: string[] = [];
  cargando: boolean = false;
  Intereses: boolean = true;
  Usuario: Usuario = {} as Usuario;
  fotoBase64: string | undefined; // Para almacenar la cadena base64
  imagenVisualizacion: string = '';

  constructor(
    private ServicioPerfilService: ServicioPerfilService,
    private token: token,private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarPerfil();
  }

  cerrarPanelMensaje(){
    this.panelMensaje=false;
  }
  confirmLogout() {
    // Eliminar el token del sessionStorage


    // Redirigir al usuario a la página de inicio de sesión
    this.router.navigate(['/logout']);

  }

  // Si el usuario cancela, regresa a la página anterior
  cancelLogout() {
    // Regresar a la página de donde vino el usuario
    this.panelLogout=false;// Redirige a la página anterior o al home si no hay historial
  }

  CerrarSesionComponent(){
    this.panelLogout=true;

  }
  cargarPerfil() {
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
      if (response.usuario.Intereses != null) {
        this.Intereses = false;
      }


      return;
    }
    this.mensaje=response.mensaje;
    this.panelMensaje=true;

  }

  asignarDatos(response: any) {
    this.Usuario = response.usuario;
    this.nombre = this.Usuario.nombre;
    this.usuario = this.Usuario.usuario;
    this.edad = this.Usuario.edad;
    this.descripcion = this.Usuario.descripcion;
    this.tipoCuenta = this.Usuario.tipoCuenta;
    this.intereses = this.Usuario.Intereses;


    // Convertir Base64 a datos URL para mostrar
    if (this.Usuario.foto) {
      this.imagenVisualizacion = this.base64ToDataURL(this.Usuario.foto);
    }
  }

  base64ToDataURL(base64: string): string {
    return `data:image/jpeg;base64,${base64}`; // Crear la cadena de datos URL
  }


}
