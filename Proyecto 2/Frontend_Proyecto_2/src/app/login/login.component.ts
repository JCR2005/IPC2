import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginService } from '../services/servicioLogin/login.service';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  usuario: string = '';
  password: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  onSubmit() {
    const credentials = {
      usuario: this.usuario,
      password: this.password
    };

    this.loginService.iniciarSesion(credentials).subscribe(
      (response: any) => {
        console.log('JWT recibido:', response.token);
        alert(response.message);
        sessionStorage.setItem('token', response.token);

        // Decodificar el token
        const decodedToken: any = this.jwt_decode(response.token);

        // Verificar el tipo de cuenta y redirigir

          this.router.navigate([response.ruta]);

        // Redirigir a la ruta correcta

      },
      (error) => {
        console.error('Error de inicio de sesión:', error);
        alert('Error de inicio de sesión. Verifica tus credenciales.');
      }
    );
  }

  private jwt_decode(token: any): any {
    const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(window.atob(base64));
  }
}


