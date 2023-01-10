import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveTaskComponent } from './Dashboard/archive-task/archive-task.component';
import { DashNavigationComponent } from './Dashboard/dash-navigation/dash-navigation.component';
import { NewTaskComponent } from './Dashboard/new-task/new-task.component';
import { TodayTaskComponent } from './Dashboard/today-task/today-task.component';
import { UserprofileComponent } from './Dashboard/user-profile/user-profile.component';
import { UserTaskCardComponent } from './Dashboard/user-task-card/user-task-card.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AuthGuardGuard } from './Guards/auth-guard.guard';
import { LandingPageComponent } from './Landing Page/landing-page/landing-page.component';
import { SignInComponent } from './Registration/sign-in/sign-in.component';
import { SignUpComponent } from './Registration/sign-up/sign-up.component';

const routes: Routes =
  [
    { path: "", component: LandingPageComponent },
    { path: "sign-up", component: SignUpComponent },
    { path: "sign-in", component: SignInComponent },
    {
      path: "dash", component: DashNavigationComponent, children: [
        { path: "task", component: UserTaskCardComponent },
        { path: "add-task", component: NewTaskComponent },
        { path: "today", component: TodayTaskComponent },
        { path: "archive", component: ArchiveTaskComponent },
        { path: "profile", component: UserprofileComponent }
      ], canActivate: [AuthGuardGuard]
    },

    //Wild Card Route for 404 request
    { path: '**', pathMatch: "full", component: ErrorPageComponent },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
