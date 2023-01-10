import { Breakpoints, BreakpointObserver } from '@angular/cdk/layout';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Observable, map, shareReplay, interval } from 'rxjs';
import { Notification } from 'src/app/Model/Notification';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { NotificationComponent } from '../notification/notification.component';
import { UserprofileComponent } from '../user-profile/user-profile.component';

@Component({
  selector: 'app-dash-navigation',
  templateUrl: './dash-navigation.component.html',
  styleUrls: ['./dash-navigation.component.css']
})
export class DashNavigationComponent implements OnInit, OnDestroy {

  // Observing screen size
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver, 
    private route : Router, 
    private service : DashService,
    private dialog:MatDialog
    ) { }
  
 
  // Storing userName to display on top navigation bar
  userName : any;

  // Array to have tasks fetched
  fetchedNotification : Array<Notification> = [];

  autoFetch = interval(2000);
  subscribedService : any;
  
  ngOnInit(): void 
  {
    this.fetch();
    this.service.getUsername().subscribe({
      next  : (data) => {this.userName = data; console.log(this.userName)},
      error : (err) => {console.log(err)}
    });
  }

  // Method to get data stored
  fetch()
  {
    this.subscribedService =  this.autoFetch.subscribe(
      ()=>{
        this.service.fetchNotification().subscribe({
          next  : (data) => { this.fetchedNotification= data},
          error : (data) => {console.log(data)}
        });

        this.service.getUsername().subscribe({
          next  : (data) => {this.userName = data; console.log(this.userName)},
          error : (err) => {console.log(err)}
        });
    });
    
  }

  // Process of signing out
  signOut()
  {
    localStorage.removeItem('JWT');
    this.service.authenticatedEmail = "";
    this.route.navigateByUrl("");
  }

  // Opening dialog box to add a task
  openDialogBox()
  {
    this.dialog.open(NotificationComponent);
  }

  // Opening dialog box to add a task
  openProfile()
  {
    const dialogRef = this.dialog.open(UserprofileComponent, { data: {userName : this.userName} })
  }
  // On destruction 
  ngOnDestroy(): void {
    this.subscribedService.unsubscribe();
  }
}
