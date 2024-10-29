import { ComponentFixture, TestBed } from '@angular/core/testing';

import { classModificarCosotAsociadoGLOBALComponent } from './Modificar-cosot-asociado(GLOBAL).component';

describe('classModificarCosotAsociadoGLOBALComponent', () => {
  let component: classModificarCosotAsociadoGLOBALComponent;
  let fixture: ComponentFixture<classModificarCosotAsociadoGLOBALComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [classModificarCosotAsociadoGLOBALComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(classModificarCosotAsociadoGLOBALComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
