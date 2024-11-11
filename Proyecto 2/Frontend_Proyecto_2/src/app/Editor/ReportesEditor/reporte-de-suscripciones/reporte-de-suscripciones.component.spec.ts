import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDesuscripcionesComponent } from './reporte-de-suscripciones.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDesuscripcionesComponent;
  let fixture: ComponentFixture<ReporteDesuscripcionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDesuscripcionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDesuscripcionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
