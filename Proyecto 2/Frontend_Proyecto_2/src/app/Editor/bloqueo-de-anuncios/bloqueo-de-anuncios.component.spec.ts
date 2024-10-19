import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloqueoDeAnunciosComponent } from './bloqueo-de-anuncios.component';

describe('BloqueoDeAnunciosComponent', () => {
  let component: BloqueoDeAnunciosComponent;
  let fixture: ComponentFixture<BloqueoDeAnunciosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BloqueoDeAnunciosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BloqueoDeAnunciosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
