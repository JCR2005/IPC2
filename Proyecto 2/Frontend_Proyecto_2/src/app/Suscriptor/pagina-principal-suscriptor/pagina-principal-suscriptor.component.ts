import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pagina-principal-suscriptor',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './pagina-principal-suscriptor.component.html',
  styleUrl: './pagina-principal-suscriptor.component.css'
})
export class PaginaPrincipalSuscriptorComponent {

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
