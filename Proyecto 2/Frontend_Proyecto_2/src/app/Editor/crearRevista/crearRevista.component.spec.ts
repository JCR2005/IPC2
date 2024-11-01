/* tslint:disable:no-unused-variable */
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CrearRevistaComponent } from './crearRevista.component';

describe('CrearRevistaComponent', () => {
  let component: CrearRevistaComponent;
  let fixture: ComponentFixture<CrearRevistaComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CrearRevistaComponent ]
      // Si tu componente usa formularios, agrega ReactiveFormsModule aquí
      // imports: [ ReactiveFormsModule ],
      // Si tienes otros módulos o servicios que necesita, agrégales aquí.
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrearRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
