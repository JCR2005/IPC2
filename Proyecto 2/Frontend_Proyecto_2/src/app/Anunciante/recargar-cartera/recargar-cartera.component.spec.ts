import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecargarCarteraComponent } from './recargar-cartera.component';

describe('RecargarCarteraComponent', () => {
  let component: RecargarCarteraComponent;
  let fixture: ComponentFixture<RecargarCarteraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecargarCarteraComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecargarCarteraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
