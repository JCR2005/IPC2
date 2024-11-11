import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeRevistasMasComentadassComponent } from './reporte-de-revistas-mas-comentadas.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeRevistasMasComentadassComponent;
  let fixture: ComponentFixture<ReporteDeRevistasMasComentadassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeRevistasMasComentadassComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeRevistasMasComentadassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
