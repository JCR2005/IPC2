import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReporteDeRevistasMasSuscritassComponent } from './reporte-de-revistas-mas-suscritas.component';

describe('ReporteDeComentariosComponent', () => {
  let component: ReporteDeRevistasMasSuscritassComponent;
  let fixture: ComponentFixture<ReporteDeRevistasMasSuscritassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReporteDeRevistasMasSuscritassComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReporteDeRevistasMasSuscritassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
