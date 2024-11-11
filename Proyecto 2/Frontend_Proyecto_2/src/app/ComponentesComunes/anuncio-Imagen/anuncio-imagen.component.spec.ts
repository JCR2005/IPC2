import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnuncioImagenComponent } from './anuncio-imagen.component';

describe('AnuncioTextoComponent', () => {
  let component: AnuncioImagenComponent;
  let fixture: ComponentFixture<AnuncioImagenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnuncioImagenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnuncioImagenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
