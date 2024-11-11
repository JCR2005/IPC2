import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilEditorComponent } from './perfilEditor.component';

describe('PerfilComponent', () => {
  let component: PerfilEditorComponent;
  let fixture: ComponentFixture<PerfilEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PerfilEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
