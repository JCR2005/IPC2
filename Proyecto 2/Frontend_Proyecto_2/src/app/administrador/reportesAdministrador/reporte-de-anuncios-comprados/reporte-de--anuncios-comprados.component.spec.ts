import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeAnunciosCompradosComponent } from './reporte-de--anuncios-comprados.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeAnunciosCompradosComponent;
  let fixture: ComponentFixture<ReporteDeAnunciosCompradosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeAnunciosCompradosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeAnunciosCompradosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
