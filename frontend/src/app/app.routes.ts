import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccueilComponent } from './accueil/accueil.component';
import { PartieComponent } from './partie/partie.component';
import {TourComponent} from './tour/tour.component'; // Un composant pour afficher la partie

export const routes: Routes = [
  { path: '', component: AccueilComponent },
  { path: 'partie/:id', component: PartieComponent },
  { path: 'tour/:id', component: TourComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
