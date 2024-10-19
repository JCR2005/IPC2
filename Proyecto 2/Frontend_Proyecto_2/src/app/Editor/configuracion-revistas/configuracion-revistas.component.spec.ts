import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracionRevistasComponent } from './configuracion-revistas.component';

describe('ConfiguracionRevistasComponent', () => {
  let component: ConfiguracionRevistasComponent;
  let fixture: ComponentFixture<ConfiguracionRevistasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfiguracionRevistasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfiguracionRevistasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
