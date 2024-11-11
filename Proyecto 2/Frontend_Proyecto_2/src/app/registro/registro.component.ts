import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RegistroService } from '../services/servicioRegistro/registro.service';


@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
})
export class RegistroComponent {
  showPassword = false;
  intereses: string[] = []; // Array para almacenar los intereses seleccionados
  seleccionoIntereses: boolean = false;
  password: string = "";
  usuario: string = "";
  tipoCuenta: string = "";
  nombre: string = "";
  edad: number = 18;
  descripcion: string = "";
  urlFoto: string = "src/main/webapp/img/user.png";
  seleccionoTipoCuenta = false;
  usuarioExiste = false;
  passwordValida = true;
  nombreValido = true;
  edadValida = true;
  descripcionValida = true;
  etiquetas: string[] = []; // Aquí se almacenarán las etiquetas seleccionadas
  etiquetasSeleccionadas: { [key: string]: boolean } = {};

  cambiarInteres() {
    // Actualiza el estado de "seleccionoIntereses" según si el usuario ha seleccionado algo
    if (this.intereses.length > 0) {
      this.seleccionoIntereses = true;
    } else {
      this.seleccionoIntereses = false;
    }
  }
  constructor(private registroService: RegistroService) {}
// Método para actualizar las etiquetas seleccionadas
toggleEtiqueta(etiqueta: string, event: Event) {
  const checkbox = event.target as HTMLInputElement; // Casting aquí
  const checked = checkbox.checked;

  this.etiquetasSeleccionadas[etiqueta] = checked; // Actualiza el estado del checkbox

  if (checked) {
    this.etiquetas.push(etiqueta); // Añadir la etiqueta si está marcada
  } else {
    this.etiquetas = this.etiquetas.filter(e => e !== etiqueta); // Quitar la etiqueta si está desmarcada
  }
  console.log(`Etiquetas seleccionadas:`, this.etiquetas);
}
  cambiar() {
    this.seleccionoTipoCuenta = true;
  }



  reiniciarDatos() {
    this.showPassword = false;
    this.password = ""; 
    this.usuario = "";
    this.tipoCuenta = "";
    this.nombre = "";
    this.edad = 18;
    this.descripcion = "";
    this.urlFoto = "/home/carlosrodriguez/Documentos/Proyecto 2/Proyecto 2/BACKEND_PROYECTO_2/src/main/webapp/img/user.png";
    this.seleccionoTipoCuenta = false;
    this.usuarioExiste = false;
    this.passwordValida = true;
    this.nombreValido = true;
    this.edadValida = true;
    this.descripcionValida = true;
    for (const etiqueta in this.etiquetasSeleccionadas) {
      this.etiquetasSeleccionadas[etiqueta] = false;
    }
  }

  onSubmit() {

    this.usuarioExiste = false;
    this.passwordValida = true;
    this.nombreValido = true;
    this.edadValida = true;
    this.descripcionValida = true;

    const formData = {
      usuario: this.usuario,
      password: this.password,
      tipoCuenta: this.tipoCuenta,
      nombre: this.nombre,
      edad: this.edad,
      descripcion: this.descripcion,
      urlFoto: this.urlFoto,
    };

    this.registroService.registrarUsuario(formData).subscribe(
      (response: any) => {
        console.log('Datos enviados correctamente:', response);
        if (response.message.trim() === 'Por favor llene todos los cambios') {
          alert(response.message);
        } else if (response.message.trim() === 'UsuarioExiste') {
          this.usuarioExiste = true;
        } else if (response.message.trim() === 'ContraceñaInvalida') {
          this.passwordValida = false;
        } else if (response.message.trim() === 'nombreInvalido') {
          this.nombreValido = false;
        } else if (response.message.trim() === 'edadInvalida') {
          this.edadValida = false;
        } else if (response.message.trim() === 'descripcionValida') {
          this.descripcionValida = false;
        } else {
          alert(response.message);
          this.reiniciarDatos();
        }


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
}
