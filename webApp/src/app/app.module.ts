import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { RegCandidatoComponent } from './candidates/components/reg-candidato/reg-candidato.component';
import { RegCandidatoService } from './candidates/services/reg-candidato.service';
import { CandidatesComponent } from './candidates/candidates.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { InfoAcademicaComponent } from './candidates/components/dashboard-cand/info-academica/info-academica.component';
import { CreateInfoAcadComponent } from './candidates/components/dashboard-cand/info-academica/create-info-acad/create-info-acad.component';
import { DashboardCandComponent } from './candidates/components/dashboard-cand/dashboard-cand.component';
import { InicioComponent } from './shared/components/inicio/inicio.component';
import { InfoTecnicaComponent } from './candidates/components/dashboard-cand/info-tecnica/info-tecnica.component';
import { CreateInfoTecComponent } from './candidates/components/dashboard-cand/info-tecnica/create-info-tec/create-info-tec.component';
import { InfoPersonalComponent } from './candidates/components/dashboard-cand/info-personal/info-personal.component';
import { InfoLaboralComponent } from './candidates/components/dashboard-cand/info-laboral/info-laboral.component';
import { CreateInfoLaboralComponent } from './candidates/components/dashboard-cand/info-laboral/create-info-laboral/create-info-laboral.component';
import { LoginComponent } from './core/auth/login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    RegCandidatoComponent,
    InicioComponent,
    CandidatesComponent,
    InfoAcademicaComponent,
    CreateInfoAcadComponent,
    DashboardCandComponent,
    InfoTecnicaComponent,
    CreateInfoTecComponent,
    InfoPersonalComponent,
    InfoLaboralComponent,
    CreateInfoLaboralComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AngularMaterialModule
  ],
  providers: [
    RegCandidatoService,
    InicioComponent
  ],
  bootstrap: [AppComponent]

})
export class AppModule { }
