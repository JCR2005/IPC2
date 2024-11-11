import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cerrar-sesion',
  standalone: true,
  imports: [],
  templateUrl: './cerrar-sesion.component.html',
  styleUrls: ['./cerrar-sesion.component.css']
})
export class CerrarSesionComponent {

  constructor(private router: Router) {}

  // Confirmar que el usuario desea cerrar sesión
  ngOnInit() {
    // Eliminar el token del sessionStorage
    sessionStorage.removeItem('token');

    // Redirigir al usuario a la página de inicio de sesión
    this.router.navigate(['/login']);


  }

  // Si el usuario cancela, regresa a la página anterior

}
