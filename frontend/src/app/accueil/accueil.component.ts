import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PartieService } from '../services/partie.service'; // Import du service PartieService

@Component({
  selector: 'app-accueil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.css'],
})
export class AccueilComponent {
  showCreateFields: boolean = false;
  showJoinFields: boolean = false;

  idPartie: number | null = null;
  username: string = '';
  nbTours: number | null = null;
  playerName: string = '';

  constructor(private router: Router, private partieService: PartieService) {}

  showCreateGameFields() {
    this.showCreateFields = true;
    this.showJoinFields = false;
  }

  showJoinGameFields() {
    this.showJoinFields = true;
    this.showCreateFields = false;
  }

  createGame() {
    if (this.playerName && this.nbTours) {
      this.partieService.createGame(this.playerName, this.nbTours).subscribe({
        next: (idPartie: number) => {
          alert(`Partie créée avec succès ! ID : ${idPartie}`);
          this.router.navigate(['/partie', idPartie]);
        },
        error: (err) => {
          console.error('Erreur lors de la création de la partie:', err);
          alert('Erreur lors de la création de la partie.');
        },
      });
    } else {
      alert('Veuillez saisir un nom de joueur et le nombre de tours.');
    }
  }

  joinGame() {
    if (this.idPartie && this.username) {
      this.partieService.joinGame(this.username, this.idPartie).subscribe({
        next: (response: number) => {
          alert(`Vous avez rejoint la partie avec succès !`);
          this.router.navigate(['/partie', this.idPartie]);
        },
        error: (err) => {
          console.error('Erreur lors de la jonction à la partie:', err);
          alert('Erreur lors de la jonction à la partie.');
        },
      });
    } else {
      alert('Veuillez saisir un ID de partie et un nom de joueur.');
    }
  }
}
