import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PartieService {
  private apiUrl = 'http://localhost:8080/api/parties';

  constructor(private http: HttpClient) {}

  createGame(nameJ1: string, nbTours: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/create/${nameJ1}/${nbTours}`, {});
  }

  joinGame(nameJ2: string, idPartie: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/join/${nameJ2}/${idPartie}`, {});
  }
  getGameState(idPartie: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${idPartie}`);
  }
}