import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';
import { AuthGuard } from './authentication/auth.guard';
import { UserService } from './services/user.service';
import { AngularFireModule } from '@angular/fire';
import { AngularFireDatabaseModule } from '@angular/fire/database';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { environment } from '../environments/environment';
import { rootRouterConfig } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatRippleModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AgmCoreModule } from '@agm/core';
import { AgmDrawingModule } from '@agm/drawing';
import { ChartsModule } from 'ng2-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { UserResolver } from './user/user.resolver';
import { HomeComponent } from './home/home.component';
import { CourseOverviewComponent } from './course-overview/course-overview.component';
import { SupportComponent } from './support/support.component';
import { PlayerOverviewComponent } from './player-overview/player-overview.component';
import { CourseMapComponent } from './course-map/course-map.component';
import { Hole01Component } from './course-overview/hole-info/hole01/hole01.component';
import { Hole02Component } from './course-overview/hole-info/hole02/hole02.component';
import { Hole03Component } from './course-overview/hole-info/hole03/hole03.component';
import { Hole04Component } from './course-overview/hole-info/hole04/hole04.component';
import { Hole05Component } from './course-overview/hole-info/hole05/hole05.component';
import { Hole06Component } from './course-overview/hole-info/hole06/hole06.component';
import { Hole07Component } from './course-overview/hole-info/hole07/hole07.component';
import { Hole08Component } from './course-overview/hole-info/hole08/hole08.component';
import { Hole09Component } from './course-overview/hole-info/hole09/hole09.component';
import { Hole10Component } from './course-overview/hole-info/hole10/hole10.component';
import { Hole11Component } from './course-overview/hole-info/hole11/hole11.component';
import { Hole12Component } from './course-overview/hole-info/hole12/hole12.component';
import { Hole13Component } from './course-overview/hole-info/hole13/hole13.component';
import { Hole14Component } from './course-overview/hole-info/hole14/hole14.component';
import { Hole15Component } from './course-overview/hole-info/hole15/hole15.component';
import { Hole16Component } from './course-overview/hole-info/hole16/hole16.component';
import { Hole17Component } from './course-overview/hole-info/hole17/hole17.component';
import { Hole18Component } from './course-overview/hole-info/hole18/hole18.component';
import { ActivePlayerTableComponent } from './player-overview/tables/active-player-table/active-player-table.component';
import { RequestsTableComponent } from './player-overview/tables/requests-table/requests-table.component';
import { WaitTimeComponent } from './course-overview/hole-info/wait-time/wait-time.component';
import { PasswordResetComponent } from './login/password-reset/password-reset.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserComponent,
    RegisterComponent,
    DashboardComponent,
    HomeComponent,
    CourseOverviewComponent,
    SupportComponent,
    PlayerOverviewComponent,
    CourseMapComponent,
    Hole01Component,
    Hole02Component,
    Hole03Component,
    Hole04Component,
    Hole05Component,
    Hole06Component,
    Hole07Component,
    Hole08Component,
    Hole09Component,
    Hole10Component,
    Hole11Component,
    Hole12Component,
    Hole13Component,
    Hole14Component,
    Hole15Component,
    Hole16Component,
    Hole17Component,
    Hole18Component,
    ActivePlayerTableComponent,
    RequestsTableComponent,
    WaitTimeComponent,
    PasswordResetComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'YOUR_KEY',
      libraries: ['places', 'drawing', 'geometry']
    }),
    AgmDrawingModule,
    ChartsModule,
    RouterModule.forRoot(rootRouterConfig, { useHash: false }),
    AngularFireModule.initializeApp(environment),
    AngularFirestoreModule,
    AngularFireAuthModule,
    AngularFireDatabaseModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatToolbarModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatSelectModule,
    MatTabsModule,
    MatRippleModule,
    MatExpansionModule,
    MatDividerModule,
    MatStepperModule,
    MatSidenavModule,
    MatListModule,
    MatRadioModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
  ],
  entryComponents: [CourseMapComponent],
  providers: [AuthService, UserService, UserResolver, AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
