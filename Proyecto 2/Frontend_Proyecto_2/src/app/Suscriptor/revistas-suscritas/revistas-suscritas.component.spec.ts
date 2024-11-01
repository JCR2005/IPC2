import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevistasSuscritasComponent } from './revistas-suscritas.component';

describe('RevistasSuscritasComponent', () => {
  let component: RevistasSuscritasComponent;
  let fixture: ComponentFixture<RevistasSuscritasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevistasSuscritasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevistasSuscritasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
