import { Component, HostListener } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    FormsModule,
    FontAwesomeModule,
  ],
  selector: 'app-pagina-principal-editor',
  templateUrl: './pagina-principal-editor.component.html',
  styleUrls: ['./pagina-principal-editor.component.css']
})
export class PaginaPrincipalEditorComponent {
  panelLogout: boolean = false;

  constructor(private router: Router) {}

  ngOnInit() {}

  @HostListener('window:popstate', ['$event'])
  onPopState(event: PopStateEvent) {
    this.CerrarSesionComponent();
  }

  CerrarSesionComponent() {
    this.panelLogout = true;
  }

  confirmLogout() {
    this.router.navigate(['/logout']);
  }

  cancelLogout() {
    this.panelLogout = false;
    this.router.navigate(['/paginaPrincipalEditor']);
  }
}
