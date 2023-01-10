import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './Angular Material/material.module';
import { SignInComponent } from './Registration/sign-in/sign-in.component';
import { SignUpComponent } from './Registration/sign-up/sign-up.component';
import { LandingPageComponent } from './Landing Page/landing-page/landing-page.component';
import { NavigationBarComponent } from './Landing Page/navigation-bar/navigation-bar.component';
import { UserTaskCardComponent } from './Dashboard/user-task-card/user-task-card.component'; 
import { HttpClientModule } from '@angular/common/http';
import { DashNavigationComponent } from './Dashboard/dash-navigation/dash-navigation.component';
import { NewTaskComponent } from './Dashboard/new-task/new-task.component';
import { TodayTaskComponent } from './Dashboard/today-task/today-task.component';
import { ArchiveTaskComponent } from './Dashboard/archive-task/archive-task.component';
import { EditTaskComponent } from './Dashboard/edit-task/edit-task.component';
import { NotificationComponent } from './Dashboard/notification/notification.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { UserprofileComponent } from './Dashboard/user-profile/user-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    LandingPageComponent,
    NavigationBarComponent,
    UserTaskCardComponent,
    DashNavigationComponent,
    NewTaskComponent,
    TodayTaskComponent,
    ArchiveTaskComponent,
    EditTaskComponent,
    NotificationComponent,
    ErrorPageComponent,
    UserprofileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
   ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
