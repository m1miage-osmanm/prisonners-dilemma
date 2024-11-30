import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccueilComponent } from './accueil/accueil.component';
import { PartieComponent } from './partie/partie.component'; // Un composant pour afficher la partie

export const routes: Routes = [
  { path: '', component: AccueilComponent },
  { path: 'partie/:id', component: PartieComponent }, // Affiche une partie spécifique
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
