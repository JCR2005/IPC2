import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaPrincipalEditorComponent } from './pagina-principal-editor.component';

describe('PaginaPrincipalEditorComponent', () => {
  let component: PaginaPrincipalEditorComponent;
  let fixture: ComponentFixture<PaginaPrincipalEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaPrincipalEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaginaPrincipalEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
