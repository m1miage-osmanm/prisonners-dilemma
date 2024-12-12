import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PartieService } from '../services/partie.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tour',
  templateUrl: './tour.component.html',
  styleUrls: ['./tour.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule],
})
export class TourComponent implements OnInit {
  idPartie!: number;
  playerDecision: string = ''; // Décision du joueur actuel
  currentTour: number = 1; // Tour actuel
  nbTours!: number; // Nombre total de tours
  isWaiting: boolean = false; // Indique si on attend l'autre joueur
  loading: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private partieService: PartieService
  ) {}

  ngOnInit(): void {
    this.idPartie = Number(this.route.snapshot.paramMap.get('id'));
    this.nbTours = 5; // Fixé ici pour simplification, peut être dynamique
  }

  jouerTour(): void {
    if (!this.playerDecision) {
      alert('Veuillez entrer votre décision.');
      return;
    }

    this.loading = true;
    this.partieService.jouerTour(this.idPartie, this.playerDecision).subscribe({
      next: () => {
        this.loading = false;
        this.isWaiting = true; // On attend que l'autre joueur joue
        this.checkDecisions();
      },
      error: (err) => {
        console.error('Erreur lors du jeu du tour:', err);
        alert('Erreur lors de l’enregistrement de votre décision.');
        this.loading = false;
      },
    });
  }

  checkDecisions(): void {
    const interval = setInterval(() => {
      this.partieService.checkTour(this.idPartie).subscribe({
        next: (bothDecisionsPresent: boolean) => {
          if (bothDecisionsPresent) {
            clearInterval(interval);
            this.isWaiting = false;
            this.playerDecision = '';
            this.currentTour++;

            if (this.currentTour > this.nbTours) {
              this.terminerPartie();
            }
          }
        },
        error: (err) => {
          console.error('Erreur lors de la vérification des décisions:', err);
          clearInterval(interval);
        },
      });
    }, 3000); // Vérifie toutes les 3 secondes
  }

  terminerPartie(): void {
    alert('La partie est terminée ! Merci d’avoir joué.');
    this.router.navigate(['/']);
  }
}
