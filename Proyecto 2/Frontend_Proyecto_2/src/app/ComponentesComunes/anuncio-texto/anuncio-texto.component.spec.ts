import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnuncioTextoComponent } from './anuncio-texto.component';

describe('AnuncioTextoComponent', () => {
  let component: AnuncioTextoComponent;
  let fixture: ComponentFixture<AnuncioTextoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnuncioTextoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnuncioTextoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
