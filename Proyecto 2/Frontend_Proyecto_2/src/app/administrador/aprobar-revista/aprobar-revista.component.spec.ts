import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AprobarRevistaComponent } from './aprobar-revista.component';

describe('AprobarRevistaComponent', () => {
  let component: AprobarRevistaComponent;
  let fixture: ComponentFixture<AprobarRevistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AprobarRevistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AprobarRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
