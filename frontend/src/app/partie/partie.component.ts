import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PartieService } from '../services/partie.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-partie',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './partie.component.html',
  styleUrls: ['./partie.component.css'],
})
export class PartieComponent implements OnInit {
  idPartie: number | null = null; // ID de la partie
  username: string = ''; // Nom du joueur
  isWaiting: boolean = true; // Indique si le joueur est en attente

  constructor(
    private route: ActivatedRoute,
    private partieService: PartieService
  ) {}

  ngOnInit(): void {
    // Récupérer les paramètres de la route (ID de la partie et nom du joueur)
    this.route.params.subscribe((params) => {
      this.idPartie = +params['idPartie'] || null;
      this.username = params['username'] || '';
      this.checkGameStatus();
    });
  }

  // Vérifie si le jeu est prêt
  checkGameStatus(): void {
    if (this.idPartie) {
      this.partieService.getGameState(this.idPartie).subscribe({
        next: (state) => {
          // Si le jeu n'est pas prêt (manque un joueur)
          if (!state.isReady) {
            this.isWaiting = true;
          } else {
            this.isWaiting = false;
          }
        },
        error: (err) => {
          console.error('Erreur lors de la récupération de l’état de la partie:', err);
          alert('Impossible de vérifier le statut de la partie.');
        },
      });
    }
  }
}
