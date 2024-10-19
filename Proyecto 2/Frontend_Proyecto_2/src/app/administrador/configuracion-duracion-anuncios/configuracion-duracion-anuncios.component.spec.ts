import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracionDuracionAnunciosComponent } from './configuracion-duracion-anuncios.component';

describe('ConfiguracionDuracionAnunciosComponent', () => {
  let component: ConfiguracionDuracionAnunciosComponent;
  let fixture: ComponentFixture<ConfiguracionDuracionAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfiguracionDuracionAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfiguracionDuracionAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
