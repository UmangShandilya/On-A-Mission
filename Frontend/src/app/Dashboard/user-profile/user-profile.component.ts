import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DashService } from 'src/app/Service/Dashoard/dash.service';
import { DashNavigationComponent } from '../dash-navigation/dash-navigation.component';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserprofileComponent implements OnInit {
  constructor(
    private _snackBar: MatSnackBar,
    private service: DashService,
    private routes: Router,
    public dialogRef: MatDialogRef<DashNavigationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { userName: any }
  ) 
  {
    this.userProfileForm.setValue({
      userName : data.userName
    });
  }

  ngOnInit(): void {
  }

  // Custom message to be displayed
  messageToUser: string = "";

  userProfileForm = new FormGroup(
    {
      userName: new FormControl(null, [Validators.required])
    }
  );

  // Function calling service
  saveProfile(): void {
    this.service.updateUserName(this.userProfileForm.value).subscribe({
      next: () => {
        this.openSnackBar("Profile Updated Successfully");
        this.routes.navigateByUrl("dash/task");
      },
      error: () => { this.openSnackBar("OOPS! Something went wrong"); }
    });
  }

  // Getters
  get userNameEntity() { return this.userProfileForm.get('userName'); }

  // Snackbar
  openSnackBar(message: string) {
    this._snackBar.open(message, "Close");
  }

  // On Destruction
  ngOnDestroy(): void { }

}