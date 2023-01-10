import { Component, OnInit } from '@angular/core';
import { Notification } from 'src/app/Model/Notification';
import { DashService } from 'src/app/Service/Dashoard/dash.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  constructor(private service : DashService) { }

  fetchedNotification : Array<Notification> = [];
  ngOnInit(): void 
  {
    this.fetch();
  }


  fetch()
  {
    this.service.fetchNotification().subscribe({
      next  : (data) => {this.fetchedNotification = data},
      error : (data) => {console.log(data)}
    });
  }

  delete(notificationID : any)
  {
    this.service.deleteNotification(notificationID).subscribe();
  }
}
