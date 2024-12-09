import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PartieService {
  private apiUrl = 'http://localhost:5050/api/parties';

  constructor(private http: HttpClient) {}

  createGame(nameJ1: string, nbTours: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/create/${nameJ1}/${nbTours}`, {});
  }

  joinGame(nameJ2: string, idPartie: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/join/${nameJ2}/${idPartie}`, {});
  }

  getEstPret(idPartie: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/${idPartie}/estPret`);
  }
  jouerTour(idPartie: number, decision: string): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/jouerTour/${idPartie}?decision=${decision}`,
      {}
    );
  }

  checkTour(idPartie: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/${idPartie}/checkDecisions`);
  }


}
