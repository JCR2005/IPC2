import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pagina-principal-administrador',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './pagina-principal-administrador.component.html',
  styleUrl: './pagina-principal-administrador.component.css'
})
export class PaginaPrincipalAdministradorComponent {

  constructor(private location: Location, private router: Router) {}

  ngOnInit() {
    this.preventBackNavigation();
  }

  preventBackNavigation() {
    history.pushState(null, '', location.href);

    window.addEventListener('popstate', () => {
      history.pushState(null, '', location.href); // Empuja un nuevo estado al historial
      this.router.navigate(["/paginaPrincipalAdministrador"]);

    });
  }
}
