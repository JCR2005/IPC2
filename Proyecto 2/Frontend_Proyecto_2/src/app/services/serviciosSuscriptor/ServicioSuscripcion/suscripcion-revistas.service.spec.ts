import { TestBed } from '@angular/core/testing';

import { SuscripcionRevistasService } from './suscripcion-revistas.service';

describe('SuscripcionRevistasService', () => {
  let service: SuscripcionRevistasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuscripcionRevistasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
