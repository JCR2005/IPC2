import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportesAdministradorComponent } from './reportes.component';

describe('ReportesComponent', () => {
  let component: ReportesAdministradorComponent;
  let fixture: ComponentFixture<ReportesAdministradorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportesAdministradorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportesAdministradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
