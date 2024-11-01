import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizacionRevistaComponent } from './visualizacion-revista.component';

describe('VisualizacionRevistaComponent', () => {
  let component: VisualizacionRevistaComponent;
  let fixture: ComponentFixture<VisualizacionRevistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisualizacionRevistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisualizacionRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
