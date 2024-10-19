import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigPreciosAnunciosComponent } from './configuracion-costa-anuncio.component';

describe('ConfiguracionCostaAnuncioComponent', () => {
  let component: ConfigPreciosAnunciosComponent;
  let fixture: ComponentFixture<ConfigPreciosAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfigPreciosAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfigPreciosAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
