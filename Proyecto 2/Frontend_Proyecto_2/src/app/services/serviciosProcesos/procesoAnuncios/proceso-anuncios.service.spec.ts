import { TestBed } from '@angular/core/testing';

import { ProcesoAnunciosService } from './proceso-anuncios.service';

describe('ProcesoAnunciosService', () => {
  let service: ProcesoAnunciosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProcesoAnunciosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
