import { TestBed } from '@angular/core/testing';

import { ListaDeRevistasService } from './lista-de-revistas.service';

describe('ListaDeRevistasService', () => {
  let service: ListaDeRevistasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListaDeRevistasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
