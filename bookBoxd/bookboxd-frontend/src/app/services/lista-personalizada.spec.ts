import { TestBed } from '@angular/core/testing';

import { ListaPersonalizada } from './lista-personalizada';

describe('ListaPersonalizada', () => {
  let service: ListaPersonalizada;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListaPersonalizada);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
