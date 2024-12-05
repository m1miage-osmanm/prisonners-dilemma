import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { PartieService } from '../services/partie.service';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-partie',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './partie.component.html',
  styleUrls: ['./partie.component.css'],
})
export class PartieComponent implements OnInit, OnDestroy {
  idPartie!: number;
  isReady: boolean = false;
  loading: boolean = true;
  intervalSubscription!: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private partieService: PartieService
  ) {}

  ngOnInit(): void {
    this.idPartie = Number(this.route.snapshot.paramMap.get('id'));

    this.intervalSubscription = interval(3000)
      .pipe(
        switchMap(() => this.partieService.getEstPret(this.idPartie))
      )
      .subscribe({
        next: (isReady: boolean) => {
          this.isReady = isReady;
          if (this.isReady) {
            alert('La partie est prête !');
            this.router.navigate(['/game', this.idPartie]); // Redirige vers le jeu
          }
        },
        error: (err) => {
          console.error('Erreur lors de la vérification de l’état de la partie:', err);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.intervalSubscription) {
      this.intervalSubscription.unsubscribe();
    }
  }
}
