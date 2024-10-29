import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModificarCostoAsociadoComponent } from './modificar-costo-asociado.component';

describe('ModificarCostoOcultacionComponent', () => {
  let component: ModificarCostoAsociadoComponent;
  let fixture: ComponentFixture<ModificarCostoAsociadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModificarCostoAsociadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModificarCostoAsociadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
